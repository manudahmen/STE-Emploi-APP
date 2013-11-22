package steemploi.service;

import java.io.File;
import java.net.URL;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorActionForm;

public class CV extends ValidatorActionForm
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7925551978635939735L;
	private int id;
	private FormFile CvFile;
	private String ext;
	private String name="CV";
	private String filenameOriginal;
	private String originalPath;
	private String filenameFS;
	private byte[] fileData;

	private int etudiantId;
	private String version;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public FormFile getCvFile() {
		return CvFile;
	}
	public void setCvFile(FormFile cvFile) {
		CvFile = cvFile;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getFilenameOriginal() {
		return filenameOriginal;
	}
	public void setFilenameOriginal(String filenameOriginal) {
		this.filenameOriginal = filenameOriginal;
	}
	public String getOriginalPath() {
		return originalPath;
	}
	public void setOriginalPath(String originalPath) {
		this.originalPath = originalPath;
	}
	public void setFilenameFS(String filenameFS) {
		this.filenameFS = filenameFS;
	}
	public String getFilenameFS() {
		return filenameFS;
	}
	public byte[] getFileData() {
		return fileData;
	}
	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getEtudiantId() {
		return etudiantId;
	}
	public void setEtudiantId(int etudiantId) {
		this.etudiantId = etudiantId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	
}
