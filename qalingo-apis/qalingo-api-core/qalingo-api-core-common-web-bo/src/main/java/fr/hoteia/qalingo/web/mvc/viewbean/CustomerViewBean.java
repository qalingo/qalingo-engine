package fr.hoteia.qalingo.web.mvc.viewbean;

import java.io.Serializable;

public class CustomerViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -1248064749647907583L;
	private Long id;
	private int version;
	private String login;
	private String title;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private String defaultLocale;
	private boolean active;

	// private Set<CustomerAddress> addresses = new HashSet<CustomerAddress>();
	// private Set<CustomerConnectionLog> connectionLogs = new
	// HashSet<CustomerConnectionLog>();
	// private Set<CustomerMarketArea> customerMarketAreas = new
	// HashSet<CustomerMarketArea>();
	// private Set<CustomerAttribute> customerAttributes = new
	// HashSet<CustomerAttribute>();

	private String dateCreate;
	private String dateUpdate;

	private String detailsUrl;
	private String editUrl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDefaultLocale() {
		return defaultLocale;
	}

	public void setDefaultLocale(String defaultLocale) {
		this.defaultLocale = defaultLocale;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(String dateCreate) {
		this.dateCreate = dateCreate;
	}

	public String getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(String dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public String getDetailsUrl() {
		return detailsUrl;
	}

	public void setDetailsUrl(String detailsUrl) {
		this.detailsUrl = detailsUrl;
	}

	public String getEditUrl() {
		return editUrl;
	}

	public void setEditUrl(String editUrl) {
		this.editUrl = editUrl;
	}
}
