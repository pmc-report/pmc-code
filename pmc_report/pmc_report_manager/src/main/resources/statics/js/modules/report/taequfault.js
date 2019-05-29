$(function () {
	
	//初始化工厂及下级
	initShopSelected();	
	//初始化时间频率
//	frequencySelected();
	shiftSelected();
	//初始化表格上的查询条件
	inittableTitle();
	//初始化表格
	initTable();
});

function inittableTitle(params) {
	$('#equFaultTableHeader').empty();   //每次变化时清空所有子节点
	var table = '';
	var tabletdf = '<tbody>';
	var tablebody = '<tr>'
			+ '<td>车间</td>'
			+ '<td></td>'
			+ '<td>Zone</td>'
			+ '<td></td>'
			+ '<td>设备</td>'
			+ '<td></td>'
			+ '<td>时间范围从</td>'
			+ '<td></td>'
			+ '<td>班次</td>'
			+ '<td></td>'
			+ '</tr>'
			
			+ '<tr>'
			+ '<td>区域</td>'
			+ '<td></td>'
			+ '<td>工位</td>'
			+ '<td></td>'
			+ '<td>车型</td>'
			+ '<td></td>'
			+ '<td>到</td>'
			+ '<td></td>'
			+ '<td>报表生成时间</td>'
			+ '<td></td>'
			+ '</tr>'
	var tabletde = '</tbody>';
	table += (tabletdf + tablebody + tabletde);
	$('#equFaultTableHeader').html(table);
//	console.log($('#equFaultTableHeader tr:eq(0)').children('td').length);   获取首个tr下td 的个数
	var lengths = $('#equFaultTableHeader tr:eq(0)').children('td').length;
	for(var i = 1 ; i <=lengths;i++){
		if(i%2 != 0){
			$('#equFaultTableHeader td:nth-child('+i+')').css({"font-weight":"bold","width":"6%","text-align":"center"});
		}else{
			$('#equFaultTableHeader td:nth-child('+i+')').css({"width":"14%","text-align":"center"});
			$('#equFaultTableHeader td:nth-child('+i+')').html("All");
		}
	}
	$('#equFaultTableHeader td:nth-child(1)').css("border-left","1px solid #000");
	$('#equFaultTableHeader tr:last-child td:last-child').html('');
	
	if(params != null && params != ''){
		
		if(params.shop != null && params.shop.trim() != ''){
			$('#equFaultTableHeader tr:eq(0) td:eq(1)').html(params.shop);
		}else{
			$('#equFaultTableHeader tr:eq(0) td:eq(1)').html("All");
		}
		
		if(params.zone != null && params.zone.trim() != ''){
			$('#equFaultTableHeader tr:eq(0) td:eq(3)').html(params.zone);
		}else{
			$('#equFaultTableHeader tr:eq(0) td:eq(3)').html("All");
		}
		
		if(params.equipment != null && params.equipment.trim() != ''){
			$('#equFaultTableHeader tr:eq(0) td:eq(5)').html(params.equipment);
		}else{
			$('#equFaultTableHeader tr:eq(0) td:eq(5)').html("All");
		}
		
		if(params.sTime != null && params.sTime.trim() != ''){
			$('#equFaultTableHeader tr:eq(0) td:eq(7)').html(params.sTime);
		}else{
			$('#equFaultTableHeader tr:eq(0) td:eq(7)').html("All");
		}
		
		if(params.shift != null && params.shift.trim() != ''){
			$('#equFaultTableHeader tr:eq(0) td:eq(9)').html(params.shift);
		}else{
			$('#equFaultTableHeader tr:eq(0) td:eq(9)').html("All");
		}
		
		if(params.area != null && params.area.trim() != ''){
			$('#equFaultTableHeader tr:eq(1) td:eq(1)').html(params.area);
		}else{
			$('#equFaultTableHeader tr:eq(1) td:eq(1)').html("All");
		}
		
		if(params.station != null && params.station.trim() != ''){
			$('#equFaultTableHeader tr:eq(1) td:eq(3)').html(params.station);
		}else{
			$('#equFaultTableHeader tr:eq(1) td:eq(3)').html("All");
		}
		
		if(params.jobId != null && params.jobId.trim() != ''){
			$('#equFaultTableHeader tr:eq(1) td:eq(5)').html(params.jobId);
		}else{
			$('#equFaultTableHeader tr:eq(1) td:eq(5)').html("All");
		}
		
		if(params.eTime != null && params.eTime.trim() != ''){
			$('#equFaultTableHeader tr:eq(1) td:eq(7)').html(params.eTime);
		}else{
			$('#equFaultTableHeader tr:eq(1) td:eq(7)').html("All");
		}
		
		var mydate = new Date();
		var createTime = mydate.getFullYear() + '-'+ Appendzero(mydate.getMonth()+1) + '-' + Appendzero(mydate.getDate()) +'  '+mydate.getHours() + ':' + Appendzero(mydate.getMinutes())+':'+Appendzero(mydate.getSeconds());
		$('#equFaultTableHeader tr:eq(1) td:eq(9)').html(createTime);
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
	
	var url = baseURL + 'report/fault/list';
	if(tag=='EQUFA'){
		inittableTitle(params);
		initTable(url,params);
	}
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

function initTable(url,queryParams){
	//console.log(queryParams);
	var duration ='';
	var responseHandler = function (e) {
	      //console.log(e);
		  duration = e.duration
	      if (e.page.list !=null && e.page.list.length > 0) {
	          return { "rows": e.page.list, "total": e.page.totalCount };
	      } else {
	          return { "rows": [], "total": 0 };
	      }
	 }
	
	 var uidHandle = function (res) {
	      var html = "<a href='#'>"+ res + "</a>";
	      return html;
	 }
	 
	 var dateFormatter = function(value, row, index){
 	    return sec_to_time(value);
     } 
	 console.log(duration+"++++++++++++++++++++++++++++")
     var columns = [
          
    	  {title: '序号', align: 'center', formatter: function indexFormatter(value, row, index) {return index + 1;},
    		  footerFormatter : function(rows){
    				if(duration!=null){
    					return "总持续时间: "+sec_to_time(duration);
    				}else{
    					return "总持续时间: 0";
    				}
    			}
    	  },
          { field: 'line', title: '区域', align: 'center', sortable:false },
          { field: 'zone', title: 'Zone', align: 'center', sortable:false }, 
          { field: 'station', title: '工位', align: 'center', sortable:false }, 
          { field: 'facilityId', title: '设备号', align: 'center'},
          { field: 'facilityDesc', title: '设备名称', halign:'center' }, 
          { field: 'jobId', title: '车型', align: 'center'}, 
          { field: 'faultWord1', title: 'fault_Word1', align: 'center' },
          { field: 'faultWord2', title: 'fault_Word2', align: 'center' }, 
          { field: 'faultWord3', title: 'fault_Word3', align: 'center' }, 
          { field: 'posWord31', title: 'Word31', align: 'center' },
          { field: 'faultDescription', title: '故障描述', align: 'center' }, 
          { field: 'reasonCode', title: '原因代码', align: 'center' },
          { field: 'reasonDescription', title: '原因描述', align: 'center' },
          { field: 'startTime', title: '开始时间', align: 'center', width: 90 },
          { field: 'endTime', title: '结束时间', align: 'center', width: 90 }, 
          { field: 'duration', title: '持续时间', align: 'center',formatter: dateFormatter}
      ];
     
	  $('#equFaultTable').empty();
	  $('#equFaultTable').bootstrapTable('destroy').bootstrapTable({
	      url: url,   						  //url一般是请求后台的url地址,调用ajax获取数据。此处我用本地的json数据来填充表格。
	      method: "post",                     //使用get请求到服务器获取数据
	      dataType: "json",
	      contentType: "application/x-www-form-urlencoded",
	      toolbar: "#toolbar",                //一个jQuery 选择器，指明自定义的toolbar 例如:#toolbar, .toolbar.
	    //uniqueId: 'taEquFaultId',           //每一行的唯一标识，一般为主键列
	      height: 522,						  //document.body.clientHeight-165  //动态获取高度值，可以使表格自适应页面
	      cache: false,                       // 不缓存
	      striped: true,                      // 隔行加亮
	      queryParamsType: '',           	  //设置为"undefined",可以获取pageNumber，pageSize，searchText，sortName，sortOrder 
	                                          //设置为"limit",符合 RESTFul 格式的参数,可以获取limit, offset, search, sort, order 
	      sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
	      sortable: true,                     //是否启用排序;意味着整个表格都会排序
	      sortName: 'taEquFaultId',           // 设置默认排序为 name
	      sortOrder: "asc",                   //排序方式
	      pagination: true,                   //是否显示分页（*）
	      search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
	      strictSearch: true,
	      //showColumns: true,                  //是否显示所有的列
	      //showRefresh: true,                  //是否显示刷新按钮
	      //showToggle:true,                    //是否显示详细视图和列表视图
	      //clickToSelect: true,                //是否启用点击选中行
	      minimumCountColumns: 2,             //最少允许的列数 clickToSelect: true, //是否启用点击选中行
	      pageNumber: 1,                      //初始化加载第一页，默认第一页
	      pageSize: 1000,                    	  //每页的记录行数（*）
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
	      showFooter: true,
	      onPostBody:function () {
	    	    //合并页脚(回调)
	    	    merge_footer();
	    	},
	      //hasPreviousPage: true,
	      //hasNextPage: true,
	      //lastPage: true,
	      //firstPage: true,
	      columns: columns,
	      queryParams : function(params) {
		      return {
			    	limit : params.pageSize,
		            page : params.pageNumber,
		            //keyword: params.search,//搜索
					//sortOrder: params.order,//排序
					//sortName: params.sort,//排序字段
		            
	            	area : queryParams.area,
					zone : queryParams.zone,
					eTime: queryParams.eTime,
					sTime: queryParams.sTime,
					shift: queryParams.shift,
					shop: queryParams.shop,
					jobId : queryParams.jobId,
					station : queryParams.station,
					equipment : queryParams.equipment
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
	          //console.log(data);
	      },
	      onLoadError: function (res) { 		//加载失败时执行
	          //console.log(res);
	      }
	  });
	  
	  $('#equFaultTableStyle .columns').css("margin-top","-164px");
}


function exportfault() {
	
	$("#faultshop").val($("#shop_search").val());
	$("#faultarea").val($("#area_search").val());
	$("#faultzone").val($("#zone_search").val());
	$("#faultstation").val($("#station_search").val());
	$("#faultequipment").val($('#equ_search').val());
	$("#faultjobId").val($("#jobId_search").val());
	$("#faultstime").val($("#startTime").val());
	$("#faulteTime").val($("#endTime").val());
	$("#faultshift").val($("#shift").val());
	
	$("#exportfalut").attr("action", "/pmc/report/fault/exportFault")
	$("#exportfalut").submit();
}
