package hdfsmanager.api.dao.impl;

import java.util.Collections;
import java.util.List;

import hdfsmanager.util.PathUtil;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;

import hdfsmanager.api.dao.AppStatusDao;

public class AppStatusDaoImpl implements AppStatusDao {

	private Path currentPath;
	private List<FileStatus> filesInCurrentPath;
	private FileStatus[] selectedFileStatuses;
	private PasteStatus status;

	private AppStatusDaoImpl() {
		this.currentPath = new Path("/");
		this.filesInCurrentPath = Collections.emptyList();
		this.selectedFileStatuses = new FileStatus[0];
		this.status = PasteStatus.NONE;
	}

	@Override
	public Path getCurrentPath() {
		return currentPath;
	}

	@Override
	public List<FileStatus> getFilesInCurrentPath() {
		return filesInCurrentPath;
	}

	@Override
	public FileStatus[] getSelectedFileStatuses() {
		return selectedFileStatuses;
	}

	@Override
	public PasteStatus getPasteStatus() {
		return status;
	}

	@Override
	public synchronized void setCurrentPathAndFiles(Path currentPath, List<FileStatus> filesInCurrentPath) {
		this.currentPath = currentPath;
		this.filesInCurrentPath = filesInCurrentPath;
	}

	@Override
	public synchronized void setSelectedFileStatuses(FileStatus[] fileStatuses) {
		this.selectedFileStatuses = fileStatuses;
	}

	@Override
	public synchronized void setPasteStatus(PasteStatus status) {
		this.status = status;
	}

	@Override
	public boolean isCurrentPathEqualsTo(Path aPath) {
		return currentPath != null &&
				aPath != null
				&& PathUtil.isEquals(currentPath, aPath);
	}

	private static AppStatusDao dao = null;

	public static synchronized AppStatusDao getOrCreate() {
		if (dao == null) {
			dao = new AppStatusDaoImpl();
		}
		return dao;
	}
}
