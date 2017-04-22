<#--
<textarea name="textarea"></textarea>
-->
<#macro editor
	name value="" height="230"
	fullPage="false" toolbarSet="My"
	label="" noHeight="false" required="false" colspan="" width="100" help="" helpPosition="2" colon=":" hasColon="true"
	maxlength="65535"
	onclick="" ondblclick="" onmousedown="" onmouseup="" onmouseover="" onmousemove="" onmouseout="" onfocus="" onblur="" onkeypress="" onkeydown="" onkeyup="" onselect="" onchange=""
	>
<#include "control.ftl"/><#rt/>
<textarea id="${name}" name="${name}" class="ckeditor">${value}</textarea>  
<script type="text/javascript">
$(function() {
   	var editor${name} = CKEDITOR.replace( '${name}' ,
   			{
		   		filebrowserUploadUrl : '${base+appBase}/fck/upload.do?Type=File',  
		   		filebrowserImageUploadUrl : '${base+appBase}/fck/upload.do?Type=Image',  
		   		filebrowserFlashUploadUrl : '${base+appBase}/fck/upload.do?Type=Flash',
		   		filebrowserMediaUploadURL : '${base+appBase}/fck/upload.do?Type=Media',
				filebrowserBrowseUrl : '${base}/assets/global/plugins/ckeditor/plugins/ckfinder/ckfinder.html',
			    filebrowserImageBrowseUrl : '${base}/assets/global/plugins/ckeditor/plugins/ckfinder/ckfinder.html',
			    filebrowserFlashBrowseUrl : '${base}/assets/global/plugins/ckeditor/plugins/ckfinder/ckfinder.html?type=Flash'
   		     }
   	);  
	CKFinder.setupCKEditor( editor${name}, '../../assets/global/plugins/ckeditor/plugins/ckfinder' ) ;
 });
</script>
<#include "control-close.ftl"/><#rt/>
</#macro>