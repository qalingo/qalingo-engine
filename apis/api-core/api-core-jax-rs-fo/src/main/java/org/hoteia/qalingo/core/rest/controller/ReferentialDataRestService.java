/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.rest.controller;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.LocaleUtils;
import org.hoteia.qalingo.core.pojo.ReferentialDataPojo;
import org.hoteia.qalingo.core.service.pojo.ReferentialDataPojoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Path("/referential-data/")
@Component("referentialDataRestService")
public class ReferentialDataRestService {

    @Autowired
    private ReferentialDataPojoService referentialDataService;

    @GET
    @Path("title/list/{locale}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ReferentialDataPojo> getTitlesByLocale(@PathParam("locale") final String locale) {
        return referentialDataService.getTitlesByLocale(LocaleUtils.toLocale(locale));
    }

    @GET
    @Path("title/{code}/{locale}")
    @Produces(MediaType.APPLICATION_JSON)
    public ReferentialDataPojo getTitleByLocale(@PathParam("code") final String code, @PathParam("locale") final String locale) {
        return referentialDataService.getTitleByLocale(code, LocaleUtils.toLocale(locale));
    }
    
    @GET
    @Path("state/list/{locale}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ReferentialDataPojo> getStatesByLocale(@PathParam("locale") final String locale) {
        return referentialDataService.getStatesByLocale(LocaleUtils.toLocale(locale));
    }

    @GET
    @Path("state/{code}/{locale}")
    @Produces(MediaType.APPLICATION_JSON)
    public ReferentialDataPojo getStateByLocale(@PathParam("code") final String code, @PathParam("locale") final String locale) {
        return referentialDataService.getStateByLocale(code, LocaleUtils.toLocale(locale));
    }
    
    @GET
    @Path("area/list/{locale}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ReferentialDataPojo> getAreasByLocale(@PathParam("locale") final String locale) {
        return referentialDataService.getAreasByLocale(LocaleUtils.toLocale(locale));
    }

    @GET
    @Path("area/{code}/{locale}")
    @Produces(MediaType.APPLICATION_JSON)
    public ReferentialDataPojo getAreaByLocale(@PathParam("code") final String code, @PathParam("locale") final String locale) {
        return referentialDataService.getAreaByLocale(code, LocaleUtils.toLocale(locale));
    }
    
    @GET
    @Path("country/list/{locale}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ReferentialDataPojo> getCountrysByLocale(@PathParam("locale") final String locale) {
        return referentialDataService.getCountriesByLocale(LocaleUtils.toLocale(locale));
    }

    @GET
    @Path("country/{code}/{locale}")
    @Produces(MediaType.APPLICATION_JSON)
    public ReferentialDataPojo getCountryByLocale(@PathParam("code") final String code, @PathParam("locale") final String locale) {
        return referentialDataService.getCountryByLocale(code, LocaleUtils.toLocale(locale));
    }

}