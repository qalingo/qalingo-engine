/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.catalog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.web.mvc.form.AssetForm;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractBusinessBackofficeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("assetController")
public class AssetController extends AbstractBusinessBackofficeController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ProductService productMarketingService;
	
	@RequestMapping(value = BoUrls.ASSET_DETAILS_URL, method = RequestMethod.GET)
	public ModelAndView assetDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.ASSET_DETAILS.getVelocityPage());

		final String currentAssetId = request.getParameter(RequestConstants.REQUEST_PARAMETER_ASSET_ID);
		final Asset asset = productMarketingService.getProductMarketingAssetById(currentAssetId);
		
		if(asset != null){
			initRuleDetailsPage(request, response, modelAndView, asset);
		} else {
			final String url = requestUtil.getLastRequestUrl(request);
			return new ModelAndView(new RedirectView(url));
		}
		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.ASSET_EDIT_URL, method = RequestMethod.GET)
	public ModelAndView display(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.ASSET_EDIT.getVelocityPage());
		final RequestData requestData = requestUtil.getRequestData(request);
		final String currentAssetId = request.getParameter(RequestConstants.REQUEST_PARAMETER_ASSET_ID);
		if(StringUtils.isNotEmpty(currentAssetId)){
			final Asset asset = productMarketingService.getProductMarketingAssetById(currentAssetId);

			modelAndView.addObject(ModelConstants.ASSET_VIEW_BEAN, backofficeViewBeanFactory.buildViewBeanAsset(requestUtil.getRequestData(request), asset));
			modelAndView.addObject(ModelConstants.ASSET_FORM, backofficeFormFactory.buildProductMarketingAssetForm(requestData, asset));
			return modelAndView;
		} else {
			final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.HOME, requestUtil.getRequestData(request));
	        return new ModelAndView(new RedirectView(urlRedirect));
		}
	}
	
	@RequestMapping(value = BoUrls.ASSET_EDIT_URL, method = RequestMethod.POST)
	public ModelAndView assetEdit(final HttpServletRequest request, final HttpServletResponse response, @Valid AssetForm assetForm,
								BindingResult result, ModelMap modelMap) throws Exception {

		if (result.hasErrors()) {
			return display(request, response, modelMap);
		}
		
		final String currentAssetId = assetForm.getId();
		final Asset asset = productMarketingService.getProductMarketingAssetById(currentAssetId);
		
		MultipartFile multipartFile = assetForm.getFile();
		if(multipartFile != null){
			long size = multipartFile.getSize();
			asset.setFileSize(size);
		}
		
		try {
			if (multipartFile.getSize() > 0) {
				String pathProductMarketingImage = multipartFile.getOriginalFilename();
				String assetFileRootPath = engineSettingService.getAssetFileRootPath().getDefaultValue();
				assetFileRootPath.replaceAll("\\\\", "/");
				if(assetFileRootPath.endsWith("/")){
					assetFileRootPath = assetFileRootPath.substring(0, assetFileRootPath.length() - 1);
				}
				String assetProductMarketingFilePath = engineSettingService.getAssetProductMarketingFilePath().getDefaultValue();
				assetProductMarketingFilePath.replaceAll("\\\\", "/");
				if(assetProductMarketingFilePath.endsWith("/")){
					assetProductMarketingFilePath = assetProductMarketingFilePath.substring(0, assetProductMarketingFilePath.length() - 1);
				}
				if(!assetProductMarketingFilePath.startsWith("/")){
					assetProductMarketingFilePath = "/" + assetProductMarketingFilePath;
				}
				
				String absoluteFilePath = assetFileRootPath + assetProductMarketingFilePath + "/" + asset.getType().getPropertyKey().toLowerCase() + "/"  + pathProductMarketingImage;
				
				InputStream inputStream = multipartFile.getInputStream();
				URI url = new URI(absoluteFilePath);
				File fileAsset;
				try {
					fileAsset = new File(url);
				} catch(IllegalArgumentException e) {
					fileAsset = new File(url.getPath());
				}
				OutputStream outputStream = new FileOutputStream(fileAsset);
				int readBytes = 0;
				byte[] buffer = new byte[8192];
				while ((readBytes = inputStream.read(buffer, 0, 8192)) != -1) {
					outputStream.write(buffer, 0, readBytes);
				}
				outputStream.close();
				inputStream.close();
				asset.setPath(pathProductMarketingImage);
			}
			
			// UPDATE ASSET
			webBackofficeService.createOrUpdateProductMarketingAsset(asset, assetForm);

		} catch (Exception e) {
			logger.error("Can't save/update asset file!", e);
		}
		
		final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.ASSET_DETAILS, requestUtil.getRequestData(request), asset);
        return new ModelAndView(new RedirectView(urlRedirect));
	}

	protected void initRuleDetailsPage(final HttpServletRequest request, final HttpServletResponse response, 
											final ModelAndViewThemeDevice modelAndView, final Asset asset) throws Exception {
		modelAndView.addObject(ModelConstants.ASSET_VIEW_BEAN, backofficeViewBeanFactory.buildViewBeanAsset(requestUtil.getRequestData(request), asset));
	}
}