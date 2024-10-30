package org.java.passwordmanager.objects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Registro {
    public static Integer id = 0;
    private String siteName;
    private String username;
    private String password;
    private String url;
    private String notes;
    private CamposExtra camposExtra;
    private List<Tag> tags;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private LocalDateTime expirationDate;
    private Icon icon;

    public Registro(String siteName, String username, String password, String url, String notes, CamposExtra camposExtra, List<Tag> tags, LocalDateTime creationDate, LocalDateTime updateDate, LocalDateTime expirationDate, Icon icon) {
        id++;
        this.siteName = siteName;
        this.username = username;
        this.password = password;
        this.url = url;
        this.notes = notes != null ? notes : ""; // Establece una cadena vacía si es nulo
        this.camposExtra = camposExtra;
        this.tags = tags;
        this.creationDate = creationDate != null ? creationDate : LocalDateTime.now();
        this.updateDate = updateDate != null ? updateDate : LocalDateTime.now();
        this.expirationDate = expirationDate;
        this.icon = icon;
    }

    // Métodos para obtener las fechas formateadas en ISO
    public String getFormattedCreationDate() {
        return creationDate.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public String getFormattedUpdateDate() {
        return updateDate.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public String getFormattedExpirationDate() {
        return expirationDate != null ? expirationDate.format(DateTimeFormatter.ISO_DATE_TIME) : "No expiration date";
    }



    public Integer getId() {return id;}
    public String getSiteName() {return siteName;}
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public String getUrl() {return url;}
    public String getNotes() {return notes;}
    public CamposExtra getCamposExtra() {return camposExtra;}
    public List<Tag> getTags() {return tags;}
    public LocalDateTime getCreationDate() {return creationDate;}
    public LocalDateTime getUpdateDate() {return updateDate;}
    public LocalDateTime getExpirationDate() {return expirationDate;}
    public Icon getIcon() {return icon;}

    public void setSiteName(String siteName) {this.siteName = siteName;}
    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
    public void setUrl(String url) {this.url = url;}
    public void setNotes(String notes) {this.notes = notes;}
    public void setCamposExtra(CamposExtra camposExtra) {this.camposExtra = camposExtra;}
    public void setTags(List<Tag> tags) {this.tags = tags;}
    public void setUpdateDate(LocalDateTime updateDate) {this.updateDate = updateDate;}
    public void setExpirationDate(LocalDateTime expirationDate) {this.expirationDate = expirationDate;}
    public void setIcon(Icon icon) {this.icon = icon;}

    @Override
    public String toString() {
        return "Registro{" +
                "id=" + id +
                ", siteName='" + siteName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                ", notes='" + notes + '\'' +
                ", camposExtra=" + camposExtra +
                ", tags=" + tags +
                ", creationDate=" + getFormattedCreationDate() +
                ", updateDate=" + getFormattedUpdateDate() +
                ", expirationDate=" + getFormattedExpirationDate() +
                ", icon=" + icon +
                '}';
    }

}
