/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.dao.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Blob;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.ServerDao;
import fr.hoteia.qalingo.core.domain.ServerStatus;

@Transactional
@Repository("serverDao")
public class ServerDaoImpl extends AbstractGenericDaoImpl implements ServerDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public ServerStatus getServerStatusById(Long serverStatusId) {
		return em.find(ServerStatus.class, serverStatusId);
	}

    /**
     * @throws IOException
     * @see fr.hoteia.qalingo.core.dao.impl.ServerDaoImpl#saveOrUpdateServerStatus(ServerStatus serverStatus, String message)
     */
    public void saveOrUpdateServerStatus(final ServerStatus serverStatus, final String message) throws IOException {
        Session session = (Session) em.getDelegate();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(message);
        oos.flush();
        oos.close();
        bos.close();

        byte[] data = bos.toByteArray();

        Blob blob = Hibernate.getLobCreator(session).createBlob(data);

        serverStatus.setMessageContent(blob);

        saveOrUpdateServerStatus(serverStatus);
    }
    
	public void saveOrUpdateServerStatus(ServerStatus serverStatus) {
		if(serverStatus.getId() == null){
			em.persist(serverStatus);
		} else {
			em.merge(serverStatus);
		}
	}

	public void deleteServerStatus(ServerStatus serverStatus) {
		em.remove(serverStatus);
	}

}
