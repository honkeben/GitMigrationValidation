package ssc.swarc.helper.vcs.migration.valdiator.controller.vcs.reader.svn;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import ssc.swarc.helper.vcs.migration.valdiator.controller.util.AnalysisMode;
import ssc.swarc.helper.vcs.migration.valdiator.controller.util.VCSType;
import ssc.swarc.helper.vcs.migration.valdiator.controller.vcs.reader.AbstractVCSReader;
import ssc.swarc.helper.vcs.migration.valdiator.model.Credentials;
import ssc.swarc.helper.vcs.migration.valdiator.model.Revision;
import ssc.swarc.helper.vcs.migration.valdiator.model.VCSTreeModel;

/**
 * @author Benjamin Honke
 *
 */
public class SVNReader extends AbstractVCSReader {
	Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	/*
	 * Default values:
	 */
	long startRevision = 0;
	long endRevision = -1;// HEAD (the latest) revision

	// Attributes requested from the properties file for svn credentials
	private static String svnUrlProp = "svn_url";

	private String trunk = "";
	private String branches = "";
	private String tags = "";

	private SVNRepository repository = null;
	String repositoryName = "";

	SVNReader() {
		super();
	}

	public SVNReader(Credentials credits) {
		super();
		user = credits.getUser();
		pwd = credits.getPW();
		trunk = credits.getPathTrunk();
		branches = credits.getPathBranch();
		tags = credits.getPathTag();
		loadProperties();
		initReader();
	}

	@Override
	protected void loadProperties() {
		if (properties != null) {
			url = properties.getProperty(svnUrlProp);
		}
	}

	@Override
	protected void initReader() {
		/*
		 * Initializes the library (it must be done before ever using the library
		 * itself)
		 */
		setupLibrary();
	}

	@Override
	public void startReading() {
		VCSTreeModel info = new VCSTreeModel(VCSType.SVN);
		info.setProjectName("");
		try {
			connectWithRepository(url + trunk);
			trunk.replaceFirst("/"+repositoryName, "");
			readSVNInformation(trunk, AnalysisMode.TRUNK, info);
			
			tags.replaceFirst("/"+repositoryName, "");
			connectWithRepository(url + tags);
			readSVNInformation(tags, AnalysisMode.TAGS, info);
			
			branches.replaceFirst("/"+repositoryName, "");
			connectWithRepository(url + branches);
			readSVNInformation(branches, AnalysisMode.BRANCHES, info);

			readedInfoTreeModel = info;
		} catch (SVNException svne) {
			JOptionPane.showMessageDialog(null, "SVN Credentials are incorrect", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void connectWithRepository(String url) throws SVNException {
		/*
		 * Creates an instance of SVNRepository to work with the repository.
		 */
		repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));

		/*
		 * User's authentication information (name/password) is provided via an
		 * ISVNAuthenticationManager instance.
		 */

		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(user, pwd);
		repository.setAuthenticationManager(authManager);
		repositoryName = StringUtils.substringAfterLast(repository.getRepositoryRoot(true).toString(),"/");
	}

	private void readSVNInformation(String prefix, AnalysisMode mode, VCSTreeModel info) throws SVNException {
		Collection logEntries = null;
		/*
		 * Gets the latest revision number of the repository
		 */
		try {
			endRevision = repository.getLatestRevision();
			logEntries = repository.log(new String[] { "" }, null, startRevision, endRevision, true, true);
			for (Iterator entries = logEntries.iterator(); entries.hasNext();) {
				/*
				 * gets a next SVNLogEntry
				 */
				SVNLogEntry logEntry = (SVNLogEntry) entries.next();

				Set changedPathsSet = logEntry.getChangedPaths().keySet();
				for (Iterator changedPaths = changedPathsSet.iterator(); changedPaths.hasNext();) {

					SVNLogEntryPath entryPath = logEntry.getChangedPaths().get(changedPaths.next());
					String path = entryPath.getPath();
					storeInformation(prefix, mode, info, logEntry, path);
				}
			}
		} catch (SVNException svne) {
			JOptionPane.showMessageDialog(null, "SVN Connection invalid: " + url + prefix, "Error",
					JOptionPane.ERROR_MESSAGE);
			throw svne;
		}

	}

	private void storeInformation(String prefix, AnalysisMode mode, VCSTreeModel info, SVNLogEntry logEntry,
			String path) {
		long revisionNumber = 0;
		String name = "";
		if (prefix.contains(path)) {
			
			if(AnalysisMode.TRUNK == mode) {
				name = "trunk";
			}			
			else {
				if(prefix.endsWith("/")) {
					prefix = prefix.substring(0, prefix.length());
				}
				int modeNameStartIndex = StringUtils.lastIndexOf(prefix, '/');
				String modeName = prefix.substring(modeNameStartIndex, prefix.length());
				String suffix = StringUtils.substringAfter(path, modeName);
				if(suffix.startsWith("/")) {
					suffix = suffix.substring(1, suffix.length());
				}
				name = StringUtils.substringBefore(suffix, "/");
			}
			
//			int startIndex = (path).length();
//			int endIndex = path.indexOf('/', startIndex + 1);
//			// case: not root path
//			if (startIndex >= 0 && endIndex >= 0) {
//				name = path.substring(startIndex + 1, endIndex).replace("/", "");
//			}
//			// case: root path
//			else {
//				name = path.substring(startIndex).replace("/", "");
//			}
			revisionNumber = logEntry.getRevision();
			if (name.isEmpty() && (AnalysisMode.TRUNK != mode)) {
				return;
			}
			if (AnalysisMode.TRUNK == mode) {
				info.getTrunkInfo().addRevision(
						new Revision(String.valueOf(revisionNumber), logEntry.getMessage(), logEntry.getAuthor()));
			} else if (AnalysisMode.TAGS == mode) {
				info.getTagInfo().addTag(name);
			} else if (AnalysisMode.BRANCHES == mode) {
				info.getBranchSet().putBranchCommit(String.valueOf(revisionNumber), name, logEntry.getMessage(),
						logEntry.getAuthor());
			}
		}
	}

	@Override
	protected void shutdownReader() {
		repository.closeSession();
	}

	/**
	 * Initializes the library to work with a repository via different protocols.
	 */
	private static void setupLibrary() {
		/*
		 * For using over http:// and https://
		 */
		DAVRepositoryFactory.setup();
		/*
		 * For using over svn:// and svn+xxx://
		 */
		SVNRepositoryFactoryImpl.setup();

		/*
		 * For using over file:///
		 */
		FSRepositoryFactory.setup();
	}

}
