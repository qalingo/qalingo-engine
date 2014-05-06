/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.domain;

import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="TBO_SERVER_STATUS")
public class ServerStatus extends AbstractEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 5455152426025490243L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;
    
    @Column(name="SERVER_NAME")
    private String serverName;

    @Column(name="SERVER_IP")
    private String serverIp;
    
    @Column(name="LAST_CHECK_RECEIVED")
    private Date lastCheckReceived;
    
    @Column(name="MESSAGE_CONTENT")
    @Lob
    private Blob messageContent;
    
    public ServerStatus() {
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public Date getLastCheckReceived() {
        return lastCheckReceived;
    }

    public void setLastCheckReceived(Date lastCheckReceived) {
        this.lastCheckReceived = lastCheckReceived;
    }

    public Blob getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(Blob messageContent) {
        this.messageContent = messageContent;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lastCheckReceived == null) ? 0 : lastCheckReceived.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ServerStatus other = (ServerStatus) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (lastCheckReceived == null) {
            if (other.lastCheckReceived != null)
                return false;
        } else if (!lastCheckReceived.equals(other.lastCheckReceived))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ServerStatus [id=" + id + ", serverName=" + serverName + ", serverIp=" + serverIp + ", lastCheckReceived=" + lastCheckReceived + "]";
    }

}