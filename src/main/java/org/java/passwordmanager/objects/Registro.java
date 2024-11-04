package org.java.passwordmanager.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.java.passwordmanager.deserializers.LocalDateTimeDeserializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Registro {
    @JsonProperty
    private Integer id;

    @JsonProperty("site_name")
    private String siteName;

    @JsonProperty
    private String username;

    @JsonProperty
    private String password;

    @JsonProperty
    private String url;

    @JsonProperty
    private String notes;

    @JsonProperty("extra_fields")
    private CamposExtra camposExtra;

    @JsonProperty
    private List<Tag> tags;

    @JsonProperty("creation_date")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime creationDate;

    @JsonProperty("update_date")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateDate;

    @JsonProperty("expiration_date")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime expirationDate;

    @JsonProperty
    private Icon icon;

    public static int contadorId = 0;

    public Registro(){}

    public Registro(String siteName, String username, String password, String url, String notes,
                    CamposExtra camposExtra, List<Tag> tags, LocalDateTime creationDate,
                    LocalDateTime updateDate, LocalDateTime expirationDate, Icon icon) {
        this.id = contadorId + 1; // Incrementa el valor estático de id para cada instancia
        this.siteName = siteName;
        this.username = username;
        this.password = password;
        this.url = url;
        this.notes = notes != null ? notes : "";
        this.camposExtra = camposExtra;
        this.tags = tags;
        this.creationDate = creationDate != null ? creationDate : LocalDateTime.now();
        this.updateDate = updateDate != null ? updateDate : LocalDateTime.now();
        this.expirationDate = expirationDate;
        this.icon = icon;
        contadorId++;
    }


    // Métodos para obtener las fechas formateadas en ISO
    @JsonIgnore
    public String getFormattedCreationDate() {
        return creationDate.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @JsonIgnore
    public String getFormattedUpdateDate() {
        return updateDate.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @JsonIgnore
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

    public void setId(Integer id) {this.id = id;}
    public void setSiteName(String siteName) {this.siteName = siteName;}
    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
    public void setUrl(String url) {this.url = url;}
    public void setNotes(String notes) {this.notes = notes;}
    public void setCamposExtra(CamposExtra camposExtra) {this.camposExtra = camposExtra;}
    public void setTags(List<Tag> tags) {this.tags = tags;}
    public void setUpdateDate(LocalDateTime updateDate) {this.updateDate = updateDate;}
    public void setExpirationDate(LocalDateTime expirationDate) {this.expirationDate = expirationDate;}
    public void setCreationDate(LocalDateTime creationDate) {this.creationDate = creationDate;}
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
