$(function () {
	//初始化工厂及下级
	initShopSelected();	
	//初始化时间频率
//	frequencySelected();
	shiftSelected();
	//初始化表格上的查询条件
	inittableTitle();
	selectChange();
});

function inittableTitle(params) {
	$('#faultOrderTableHeader').empty();   //每次变化时清空所有子节点
	var table = '';
	var tabletdf = '<tbody>';
	var tablebody = '<tr>'
			+ '<td>车间</td>'
			+ '<td></td>'
			+ '<td>区域</td>'
			+ '<td></td>'
			+ '<td>Zone</td>'
			+ '<td></td>'
			+ '<td>报表生成时间</td>'
			+ '<td></td>'
			+ '</tr>'
	var tabletde = '</tbody>';
	table += (tabletdf + tablebody + tabletde);
	$('#faultOrderTableHeader').html(table);
	var lengths = $('#faultOrderTableHeader tr:eq(0)').children('td').length;
	for(var i = 1 ; i <=lengths;i++){
		if(i%2 != 0){
			$('#faultOrderTableHeader td:nth-child('+i+')').css({"font-weight":"bold","width":"6%","text-align":"center"});
		}else{
			$('#faultOrderTableHeader td:nth-child('+i+')').css({"width":"14%","text-align":"center"});
			$('#faultOrderTableHeader td:nth-child('+i+')').html("All");
		}
	}
	$('#faultOrderTableHeader td:nth-child(1)').css("border-left","1px solid #000");
	$('#faultOrderTableHeader tr:last-child td:last-child').html('');
	
	if(params != null && params != ''){
		
		if(params.shop != null && params.shop.trim() != ''){
			$('#faultOrderTableHeader tr:eq(0) td:eq(1)').html(params.shop);
		}else{
			$('#faultOrderTableHeader tr:eq(0) td:eq(1)').html("All");
		}
		
		if(params.area != null && params.area.trim() != ''){
			$('#faultOrderTableHeader tr:eq(0) td:eq(3)').html(params.area);
		}else{
			$('#faultOrderTableHeader tr:eq(0) td:eq(3)').html("All");
		}
		
		if(params.zone != null && params.zone.trim() != ''){
			$('#faultOrderTableHeader tr:eq(0) td:eq(5)').html(params.zone);
		}else{
			$('#faultOrderTableHeader tr:eq(0) td:eq(5)').html("All");
		}

		var mydate = new Date();
		var createTime = mydate.getFullYear() + '-'+ Appendzero(mydate.getMonth()+1) + '-' + Appendzero(mydate.getDate()) +'  '+mydate.getHours() + ':' + Appendzero(mydate.getMinutes())+':'+Appendzero(mydate.getSeconds());
		$('#faultOrderTableHeader tr:eq(0) td:eq(7)').html(createTime);
	}
}

function Appendzero(obj){
	if (obj < 10) {
		return "0" + "" + obj;
	} else {
		return obj;
	}
}

//合并页脚
function merge_footer() {
    //获取table表中footer 并获取到这一行的所有列
    var footer_tbody = $('.fixed-table-footer table tbody');
    var footer_tr = footer_tbody.find('>tr');
    var footer_td = footer_tr.find('>td');
    var footer_td_1 = footer_td.eq(0);
    //由于我们这里做统计只需要两列，故可以将除第一列与最后一列的列全部隐藏，然后再设置第一列跨列
    //遍历隐藏中间的列 下标从1开始
    for(var i=1;i<footer_td.length;i++) {
        footer_td.eq(i).hide();
    }
    //设置跨列
    footer_td_1.attr('colspan', footer_td.length-1).show();
    //这里可以根据自己的表格来设置列的宽度 使对齐
    footer_td_1.attr('width', "100%").show();
}

function queryReport(tag,params){
	
	var url = baseURL + 'report/order';
	if(tag=='order'){
		inittableTitle(params);
		initCurrDurTable(url,params);
		initCurrOccTable(url,params);
		initPreDurTable(url,params);
		initPreOccTable(url,params);
	}
	clearForm("exportfalut");
}

/**
 * 时间秒数格式化
 * @param s 时间戳（单位：秒）
 * @returns {*} 格式化后的时分秒
 */
var sec_to_time = function(s) {
    var t;
    if(s > -1){
        var hour = Math.floor(s/3600);
        var min = Math.floor(s/60) % 60;
        var sec = s % 60;
        if(hour < 10) {
            t = '0'+ hour + ":";
        } else {
            t = hour + ":";
        }

        if(min < 10){t += "0";}
        t += min + ":";
        if(sec < 10){t += "0";}
        t += sec.toFixed(0);
    }
    return t;
}

function initCurrDurTable(url,queryParams){
	var responseHandler = function (e) {
		  duration = e.duration
	      if (e.list !=null && e.list.length > 0) {
	          return { "rows": e.list, "total": e.list.length };
	      } else {
	          return { "rows": [], "total": 0 };
	      }
	 }
	
	 var uidHandle = function (res) {
	      var html = "<a href='#'>"+ res + "</a>";
	      return html;
	 }
	 
	//字体格式化
	 var fontFormatter = function(value,row,index) {
		var dom = '';
		if(value!=null){
			dom = '<span style="font-size:12px">'+value+'</span>';
		}else{
			dom = '<span style="font-size:12px"> </span>';
		}
          
      return dom;  
	 }
	 
	 var dateFormatter = function(value, row, index){
 	    return value.toFixed(2);
     } 
     var columns = [
    	 	[{
	             title : "<span>Current Shift</span>",
	             halign : "center",
	             align : "center",
	             colspan : 5,
	             height :10
	       }],
    	  [{title: '序号', align: 'center', formatter: function indexFormatter(value, row, index) {return index + 1;}, width: 10},
    	  { field: 'description', title: 'Top 10 Faults by Duration', align: 'left',formatter:fontFormatter, width: 240 }, 
          { field: 'stn', title: 'Stn', align: 'center', width: 30},
          { field: 'occ', title: 'Occ', align: 'center', width: 15},
          { field: 'mins', title: 'Mins', align: 'center',formatter: dateFormatter, width: 30}]
      ];
     
	  $('#currentfaultDurTable').empty();
	  $('#currentfaultDurTable').bootstrapTable('destroy').bootstrapTable({
	      url: url+'/currDurList',   						  //url一般是请求后台的url地址,调用ajax获取数据。此处我用本地的json数据来填充表格。
	      method: "post",                     //使用get请求到服务器获取数据
	      dataType: "json",
	      contentType: "application/x-www-form-urlencoded",
	     // toolbar: "#toolbar",                //一个jQuery 选择器，指明自定义的toolbar 例如:#toolbar, .toolbar.
	    //uniqueId: 'taEquFaultId',           //每一行的唯一标识，一般为主键列
	    //  height: 522,						  //document.body.clientHeight-165  //动态获取高度值，可以使表格自适应页面
	      cache: false,                       // 不缓存
	      striped: true,                      // 隔行加亮
	      queryParamsType: '',           	  //设置为"undefined",可以获取pageNumber，pageSize，searchText，sortName，sortOrder 
	                                          //设置为"limit",符合 RESTFul 格式的参数,可以获取limit, offset, search, sort, order 
	      sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
	      sortable: true,                     //是否启用排序;意味着整个表格都会排序
	      sortName: 'taEquFaultId',           // 设置默认排序为 name
	      sortOrder: "asc",                   //排序方式
	     // pagination: true,                   //是否显示分页（*）
	      search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
	      strictSearch: true,
	      //showColumns: true,                  //是否显示所有的列
	      //showRefresh: true,                  //是否显示刷新按钮
	      //showToggle:true,                    //是否显示详细视图和列表视图
	      //clickToSelect: true,                //是否启用点击选中行
	      //minimumCountColumns: 2,             //最少允许的列数 clickToSelect: true, //是否启用点击选中行
	      //pageNumber: 1,                      //初始化加载第一页，默认第一页
	      //pageSize: 1000,                    	  //每页的记录行数（*）
	      //pageList: [10, 25, 50, 100],     	  //可供选择的每页的行数（*）
	      //showExport: true,  				  //是否显示导出按钮  
		  //exportDataType:'all', 			  //导出所有数据
	      //buttonsAlign:"right",  			  //按钮位置  
	      //exportTypes:['excel','csv','txt','xml','word'],  //导出文件类型  
	      //Icons:'glyphicon-export',  
	      //smartDisplay: true,					//智能显示分页按钮
	      //paginationPreText: "上一页",
	      //paginationNextText: "下一页",
	      responseHandler: responseHandler,
	      //showFooter: true,
	      /*onPostBody:function () {
	    	    //合并页脚(回调)
	    	    merge_footer();
	    	},*/
	      //hasPreviousPage: true,
	      //hasNextPage: true,
	      //lastPage: true,
	      //firstPage: true,
	      columns: columns,
	      queryParams : function(params) {
		      return {
	            	area : queryParams.area,
					zone : queryParams.zone,
					shop: queryParams.shop,
		      	}
		  },
	      exportOptions : {  
	          ignoreColumn: [0],  				//忽略某一列的索引  
	          fileName: '设备故障报表',  			//文件名称设置  
	          worksheetName: '设备故障',  			//表格工作区名称  
	          tableName: '设备故障报表',  
	          excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],  
	          onMsoNumberFormat: function DoOnMsoNumberFormat(cell, row, col) {  
					               var result = "";  
					               if (row > 0 && col == 0)  
					                   result = "\\@";  
					               return result;  
	           		}    
	      }, 
	      onLoadSuccess: function (data) { 		//加载成功时执行
	    	 // exportAll(queryParams);
	    	  setPorpById('exportBtn','disabled',false);
	      },
	      onLoadError: function (res) { 		//加载失败时执行
	          //console.log(res);
	      }
	  });
	  
	  $('#equFaultTableStyle .columns').css("margin-top","-164px");
}


function initCurrOccTable(url,queryParams){
	var responseHandler = function (e) {
	      //console.log(e);
		  duration = e.duration
	      if (e.list !=null && e.list.length > 0) {
	          return { "rows": e.list, "total": e.list.length };
	      } else {
	          return { "rows": [], "total": 0 };
	      }
	 }
	
	 var uidHandle = function (res) {
	      var html = "<a href='#'>"+ res + "</a>";
	      return html;
	 }
	 
	 var dateFormatter = function(value, row, index){
	 	    return value.toFixed(2);
	 } 
	 
	//字体格式化
	 var fontFormatter = function(value,row,index) {
		var dom = '';
		if(value!=null){
			dom = '<span style="font-size:12px">'+value+'</span>';
		}else{
			dom = '<span style="font-size:12px"> </span>';
		}
          
      return dom;  
	 }
	 
     var columns = [
    	 	[{
	             title : "<span>Current Shift</span>",
	             halign : "center",
	             align : "center",
	             colspan : 5
	       }],
	       [{title: '序号', align: 'center', formatter: function indexFormatter(value, row, index) {return index + 1;}, width: 10},
	       { field: 'description', title: 'Top 10 Faults by Occurence', align: 'left',formatter:fontFormatter, width: 240 }, 
           { field: 'stn', title: 'Stn', align: 'center', width: 30},
           { field: 'occ', title: 'Occ', align: 'center', width: 15},
           { field: 'mins', title: 'Mins', align: 'center',formatter: dateFormatter, width: 30}]
      ];
     
	  $('#currentfaultOccTable').empty();
	  $('#currentfaultOccTable').bootstrapTable('destroy').bootstrapTable({
	      url: url+'/currOccList',   						  //url一般是请求后台的url地址,调用ajax获取数据。此处我用本地的json数据来填充表格。
	      method: "post",                     //使用get请求到服务器获取数据
	      dataType: "json",
	      contentType: "application/x-www-form-urlencoded",						  //document.body.clientHeight-165  //动态获取高度值，可以使表格自适应页面
	      cache: false,                       // 不缓存
	      striped: true,                      // 隔行加亮
	      sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
	      sortable: true,                     //是否启用排序;意味着整个表格都会排序
	      sortName: 'taEquFaultId',           // 设置默认排序为 name
	      sortOrder: "asc",                   //排序方式
	      search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
	      strictSearch: true,
	      responseHandler: responseHandler,
	      columns: columns,
	      queryParams : function(params) {
		      return {
	            	area : queryParams.area,
					zone : queryParams.zone,
					shop: queryParams.shop,
		      	}
		  },
	      exportOptions : {  
	          ignoreColumn: [0],  				//忽略某一列的索引  
	          fileName: '设备故障报表',  			//文件名称设置  
	          worksheetName: '设备故障',  			//表格工作区名称  
	          tableName: '设备故障报表',  
	          excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],  
	          onMsoNumberFormat: function DoOnMsoNumberFormat(cell, row, col) {  
					               var result = "";  
					               if (row > 0 && col == 0)  
					                   result = "\\@";  
					               return result;  
	           		}    
	      }, 
	      onLoadSuccess: function (data) { 		//加载成功时执行
	    	  setPorpById('faultBtn','disabled',false);
	      },
	      onLoadError: function (res) { 		//加载失败时执行
	      }
	  });
}

function initPreDurTable(url,queryParams){
	var responseHandler = function (e) {
	      //console.log(e);
		  duration = e.duration
	      if (e.list !=null && e.list.length > 0) {
	          return { "rows": e.list, "total": e.list.length };
	      } else {
	          return { "rows": [], "total": 0 };
	      }
	 }
	
	 var uidHandle = function (res) {
	      var html = "<a href='#'>"+ res + "</a>";
	      return html;
	 }
	 
	//字体格式化
	 var fontFormatter = function(value,row,index) {
		var dom = '';
		if(value!=null){
			dom = '<span style="font-size:12px">'+value+'</span>';
		}else{
			dom = '<span style="font-size:12px"> </span>';
		}
          
      return dom;  
	 }
	 
	 var dateFormatter = function(value, row, index){
	 	    return value.toFixed(2);
	 } 
     var columns = [
    	 	[{
	             title : "<span>Previous Shift</span>",
	             halign : "center",
	             align : "center",
	             colspan : 5
	       }],
	       [{title: '序号', align: 'center', formatter: function indexFormatter(value, row, index) {return index + 1;}, width: 10},
	       { field: 'description', title: 'Top 10 Faults by Duration', align: 'left',formatter:fontFormatter, width: 240 }, 
           { field: 'stn', title: 'Stn', align: 'center', width: 30},
           { field: 'occ', title: 'Occ', align: 'center', width: 15},
           { field: 'mins', title: 'Mins', align: 'center',formatter: dateFormatter, width: 30}]
      ];
     
	  $('#prefaultDurTable').empty();
	  $('#prefaultDurTable').bootstrapTable('destroy').bootstrapTable({
	      url: url+'/preDurList',   						  //url一般是请求后台的url地址,调用ajax获取数据。此处我用本地的json数据来填充表格。
	      method: "post",                     //使用get请求到服务器获取数据
	      dataType: "json",
	      contentType: "application/x-www-form-urlencoded",						  //document.body.clientHeight-165  //动态获取高度值，可以使表格自适应页面
	      cache: false,                       // 不缓存
	      striped: true,                      // 隔行加亮
	      sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
	      sortable: true,                     //是否启用排序;意味着整个表格都会排序
	      sortName: 'taEquFaultId',           // 设置默认排序为 name
	      sortOrder: "asc",                   //排序方式
	      search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
	      strictSearch: true,
	      responseHandler: responseHandler,
	      columns: columns,
	      queryParams : function(params) {
		      return {
	            	area : queryParams.area,
					zone : queryParams.zone,
					shop: queryParams.shop,
		      	}
		  },
	      exportOptions : {  
	          ignoreColumn: [0],  				//忽略某一列的索引  
	          fileName: '设备故障报表',  			//文件名称设置  
	          worksheetName: '设备故障',  			//表格工作区名称  
	          tableName: '设备故障报表',  
	          excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],  
	          onMsoNumberFormat: function DoOnMsoNumberFormat(cell, row, col) {  
					               var result = "";  
					               if (row > 0 && col == 0)  
					                   result = "\\@";  
					               return result;  
	           		}    
	      }, 
	      onLoadSuccess: function (data) { 		//加载成功时执行
	    	  setPorpById('faultBtn','disabled',false);
	      },
	      onLoadError: function (res) { 		//加载失败时执行
	      }
	  });
}

function initPreOccTable(url,queryParams){
	var responseHandler = function (e) {
	      //console.log(e);
		  duration = e.duration
	      if (e.list !=null && e.list.length > 0) {
	          return { "rows": e.list, "total": e.list.length };
	      } else {
	          return { "rows": [], "total": 0 };
	      }
	 }
	
	 var uidHandle = function (res) {
	      var html = "<a href='#'>"+ res + "</a>";
	      return html;
	 }
	 
	//字体格式化
	 var fontFormatter = function(value,row,index) {
		var dom = '';
		if(value!=null){
			dom = '<span style="font-size:12px">'+value+'</span>';
		}else{
			dom = '<span style="font-size:12px"> </span>';
		}
          
      return dom;  
	 }
	 
	 var dateFormatter = function(value, row, index){
	 	    return value.toFixed(2);
	 } 
     var columns = [
    	 	[{
	             title : "<span>Previous Shift</span>",
	             halign : "center",
	             align : "center",
	             colspan : 5
	       }],
	       [{title: '序号', align: 'center', formatter: function indexFormatter(value, row, index) {return index + 1;}, width: 10},
	       { field: 'description', title: 'Top 10 Faults by Occurence', align: 'left',formatter:fontFormatter, width: 240 }, 
           { field: 'stn', title: 'Stn', align: 'center', width: 30},
           { field: 'occ', title: 'Occ', align: 'center', width: 15},
           { field: 'mins', title: 'Mins', align: 'center',formatter: dateFormatter, width: 30}]
      ];
     
	  $('#prefaultOccTable').empty();
	  $('#prefaultOccTable').bootstrapTable('destroy').bootstrapTable({
	      url: url+'/preOccList',   						  //url一般是请求后台的url地址,调用ajax获取数据。此处我用本地的json数据来填充表格。
	      method: "post",                     //使用get请求到服务器获取数据
	      dataType: "json",
	      contentType: "application/x-www-form-urlencoded",						  //document.body.clientHeight-165  //动态获取高度值，可以使表格自适应页面
	      cache: false,                       // 不缓存
	      striped: true,                      // 隔行加亮
	      sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
	      sortable: true,                     //是否启用排序;意味着整个表格都会排序
	      sortName: 'taEquFaultId',           // 设置默认排序为 name
	      sortOrder: "asc",                   //排序方式
	      search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
	      strictSearch: true,
	      responseHandler: responseHandler,
	      columns: columns,
	      queryParams : function(params) {
		      return {
	            	area : queryParams.area,
					zone : queryParams.zone,
					shop: queryParams.shop,
		      	}
		  },
	      exportOptions : {  
	          ignoreColumn: [0],  				//忽略某一列的索引  
	          fileName: '设备故障报表',  			//文件名称设置  
	          worksheetName: '设备故障',  			//表格工作区名称  
	          tableName: '设备故障报表',  
	          excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],  
	          onMsoNumberFormat: function DoOnMsoNumberFormat(cell, row, col) {  
					               var result = "";  
					               if (row > 0 && col == 0)  
					                   result = "\\@";  
					               return result;  
	           		}    
	      }, 
	      onLoadSuccess: function (data) { 		//加载成功时执行
	    	  setPorpById('faultBtn','disabled',false);
	      },
	      onLoadError: function (res) { 		//加载失败时执行
	      }
	  });
}


function exportfault(type) {
	
	var shop = $("#shop_search").val();
	var area = $("#area_search").val();
	var zone = $("#zone_search").val();
	var params = {
			shop : shop,
			area :area,
			zone :zone,
			type :type
	} ;
	
	var form = $("#exportfalut");
	assembleParams(params);
	createReportInput(params,form);
	form.attr("action", baseURL +'report/order/export');
	form.submit();
}

function assembleParams(params){
	debugger
	var tableCurrDurparamstrleg = $('#currentfaultDurTable tbody').find('tr').length;
	var tableCurrDurparamstdleg = $('#currentfaultDurTable tbody').find('tr').eq(0).find('td').length;
	var y = 1;
	if(tableCurrDurparamstdleg>1){
		for(var a =0; a<tableCurrDurparamstrleg; a++){
			for(var b =0; b <tableCurrDurparamstdleg; b++){
				params['cd_'+y] = $.trim($('#currentfaultDurTable tbody tr:eq(' + a + ') td:eq(' + b + ')').text());
				y++;
			}
		}
	}else{
		params['cd_'+y] = '';
	}

	
	var tableCurrOccparamstrleg = $('#currentfaultOccTable tbody').find('tr').length;
	var tableCurrOccparamstdleg = $('#currentfaultOccTable tbody').find('tr').eq(0).find('td').length;
	var y = 1;
	if(tableCurrOccparamstdleg>1){
		for(var a =0; a<tableCurrOccparamstrleg; a++){
			for(var b =0; b <tableCurrOccparamstdleg; b++){
				params['co_'+y] = $.trim($('#currentfaultOccTable tbody tr:eq(' + a + ') td:eq(' + b + ')').text());
				y++;
			}
		}
	}else{
		params['co_'+y] = '';
	}
	
	var tablePreDurparamstrleg = $('#prefaultDurTable tbody').find('tr').length;
	var tablePreDurparamstdleg = $('#prefaultDurTable tbody').find('tr').eq(0).find('td').length;
	var y = 1;
	if(tablePreDurparamstdleg>1){
		for(var a =0; a<tablePreDurparamstrleg; a++){
			for(var b =0; b <tablePreDurparamstdleg; b++){
				params['pd_'+y] = $.trim($('#prefaultDurTable tbody tr:eq(' + a + ') td:eq(' + b + ')').text());
				y++;
			}
		}
	}else{
		params['pd_'+y] = '';
	}
	
	var tablePreOccparamstrleg = $('#prefaultOccTable tbody').find('tr').length;
	var tablePreOccparamstdleg = $('#prefaultOccTable tbody').find('tr').eq(0).find('td').length;
	var y = 1;
	if(tablePreOccparamstdleg>1){
		for(var a =0; a<tablePreOccparamstrleg; a++){
			for(var b =0; b <tablePreOccparamstdleg; b++){
				params['po_'+y] = $.trim($('#prefaultOccTable tbody tr:eq(' + a + ') td:eq(' + b + ')').text());
				y++;
			}
		}
	}else{
		params['po_'+y] = '';
	}
}

function createReportInput(params,form){
	var inputParams = '';
	var inputSearch = '';
	for(var index in params){
		inputSearch += '<input type = "hidden" name = "'+index+'" value = "'+params[index]+'"/>'
	}
	inputParams += inputSearch;
	form.prepend(inputParams);
}

