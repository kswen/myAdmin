<#--
表格标签：用于显示列表数据。
	value：列表数据，可以是Pagination也可以是List。
	class：table的class样式。默认"pn-ltable"。
	sytle：table的style样式。默认""。
	width：表格的宽度。默认100%。
-->
<#macro table value listAction="v_list.do" class="pn-ltable" style="" theadClass="pn-lthead" tbodyClass="pn-ltbody" width="100%" cellspacing="1">
<table class="${class}" style="${style}" width="${width}" cellspacing="${cellspacing}" cellpadding="0" border="0">
<#if value?is_sequence><#local pageList=value/><#else><#local pageList=value.list/></#if>
<#list pageList as row>
<#if row_index==0>
<#assign i=-1/>
<thead class="${theadClass}"><tr><#nested row,i,true/></tr></thead>
</#if>
<#assign i=row_index has_next=row_has_next/>
<#if row_index==0><tbody  class="${tbodyClass}"><tr onmouseover="this.bgColor='#eeeeee'" onmouseout="this.bgColor='#ffffff'"><#else><tr onmouseover="this.bgColor='#eeeeee'" onmouseout="this.bgColor='#ffffff'"></#if><#nested row,row_index,row_has_next/>
<#if !row_has_next>
</tr></tbody>
<#else>
</tr>
</#if>
</#list>
</table>
<#if !value?is_sequence>
<div class="txt_center">
<ul class="pagination pagination-lg">
    <li><a href="javascript:void(0);">共 ${value.totalCount} 条</a></li>
    <li><a href="javascript:void(0);">每页<input type="text" value="${value.pageSize}" style="height: 25px; width:30px; text-align: center !important;border: none;" onfocus="this.select();" onblur="$.cookie('_cookie_page_size',this.value,{expires:3650});" onkeypress="if(event.keyCode==13){$(this).blur();return false;}"/>条</i></a></li>
    <li <#if value.firstPage> class="prev disabled"</#if>><a href="javascript:void(0);" onclick="_gotoPage('1');" title="首页"><i class="fa fa-angle-double-left"></i></a></li>
    <li <#if value.firstPage> class="prev disabled"</#if>><a href="javascript:void(0);" onclick="_gotoPage('${value.prePage}');" title="上一页"><i class="fa fa-angle-left"></i></a></li>
    <#if (8 > value.totalPage) >
        <#list 1..value.totalPage as i>
          <li <#if value.pageNo == i> class="prev disabled"</#if>><a href="javascript:void(0);" onclick="_gotoPage('${i}');">${i}</a></li>
        </#list>
    <#else>
         <#if ((value.pageNo+8) < value.totalPage) >
            <#if value.pageNo<8>
                <#list 1..8 as i>
                    <li <#if value.pageNo == i> class="prev disabled"</#if>><a href="javascript:void(0);" onclick="_gotoPage('${i}');">${i}</a></li>
                </#list>
            <#else>
                <#list value.pageNo-4..value.pageNo+4 as i>
                    <li <#if value.pageNo == i> class="prev disabled"</#if>><a href="javascript:void(0);" onclick="_gotoPage('${i}');">${i}</a></li>
                </#list>
            </#if>
         <#else>
           <#list value.pageNo-(8-(value.totalPage-value.pageNo))..value.totalPage as i>
           		 <#if 0<i>
           		 	<li <#if value.pageNo == i> class="prev disabled"</#if>><a href="javascript:void(0);" onclick="_gotoPage('${i}');">${i}</a></li>
           		 </#if>
           </#list>
         </#if>
    </#if>
    <li <#if value.lastPage> class="prev disabled"</#if>><a href="javascript:void(0);" onclick="_gotoPage('${value.nextPage}');" title="下一页"><i class="fa fa-angle-right"></i></a></li>
    <li <#if value.lastPage> class="prev disabled"</#if>><a href="javascript:void(0);" onclick="_gotoPage('${value.totalPage}');" title="末页"><i class="fa fa-angle-double-right"></i></a></li>
    <li><a href="javascript:void(0);">当前 ${value.pageNo}/${value.totalPage} 页</a></li>
    <li><a href="javascript:void(0);">转到第<input type="text" id="_goPs" style="height: 25px; width:30px; text-align: center !important;border: none;"  onfocus="this.select();" onkeypress="if(event.keyCode==13){$('#_goPage').click();return false;}"/>页<input class="btn btn-xs blue" id="_goPage" type="button" value="转" onclick="_gotoPage($('#_goPs').val());"<#if value.totalPage==1> disabled="disabled"</#if>/></a></li>
</ul>
</div>
<script type="text/javascript">
function _gotoPage(pageNo) {
	try{
		var tableForm = $("#tableForm");
		$("input[name=pageNo]").val(pageNo);
		tableForm.action="${listAction}";
		var postData = $(tableForm).serialize();
        $.ajax({
               type: "GET",
               url: tableForm.action,
               data: postData,
               success: function(data)
               {
                   var pageContentBody = $('.page-content .page-content-body');
                   pageContentBody.html(data);
               },
   			  error: function(XMLHttpRequest, textStatus, errorThrown) { 
				var pageContentBody = $('.page-content .page-content-body');
				pageContentBody.html(XMLHttpRequest.responseText);
	          }
             });
	} catch(e) {
		alert('_gotoPage(pageNo)方法出错');
	}
}
</script>
</#if>
</#macro>