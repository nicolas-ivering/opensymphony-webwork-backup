<#include "/${parameters.templateDir}/css_xhtml/controlheader.ftl" />
<label<#rt/>
<#if parameters.id?exists>
 id="${parameters.id?html}"<#rt/>
</#if>
<#if parameters.cssClass?exists>
 class="${parameters.cssClass?html}"<#rt/>
</#if>
<#if parameters.cssStyle?exists>
 style="${parameters.cssStyle?html}"<#rt/>
</#if>
<#if parameters.cssClass?exists>
 class="${parameters.cssClass?html}"<#rt/>
</#if>
<#if parameters.for?exists>
 for="${parameters.for?html}"<#rt/>
</#if>
><#rt/>
<#if parameters.nameValue?exists>
${parameters.nameValue?html}<#t/>
</#if>
&nbsp;
</label>
<#include "/${parameters.templateDir}/css_xhtml/controlfooter.ftl" />