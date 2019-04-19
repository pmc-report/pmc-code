$(function () {
	
	//初始化工厂及下级
	initShopSelected();	
	//初始化时间频率
//	frequencySelected();
	shiftSelected();
	//初始化表格
	initTable();
});

function queryReport(tag,params){
	
	var url = baseURL + 'report/fault/list';
	if(tag=='EQUFA'){
		
		initTable(url,params);
	}
}

function initTable(url,queryParams){
	console.log(queryParams);
	
	var responseHandler = function (e) {
	      //console.log(e);
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
     var columns = [
          
    	  {title: '序号', align: 'center', formatter: function indexFormatter(value, row, index) {return index + 1}},
          { field: 'area', title: '区域', align: 'center', sortable:false },
          { field: 'zone', title: 'Zone', align: 'center', sortable:false }, 
          { field: 'station', title: '工位', align: 'center', sortable:false }, 
          { field: 'facilityId', title: '设备号', align: 'center', sortable: true, clickToSelect: false, sortName: "facilityId", order:"asc" },
          { field: 'facilityDesc', title: '设备名称', halign:'center' }, 
          { field: 'jobId', title: '车型', align: 'center'}, 
          { field: 'faultWord1', title: 'Word1', align: 'center' },
          { field: 'faultWord2', title: 'Word2', align: 'center' }, 
          { field: 'faultWord3', title: 'Word3', align: 'center' }, 
          { field: 'posWord31', title: 'Word31', align: 'center' },
          { field: 'faultDescription', title: '故障描述', align: 'center' }, 
          { field: 'reasonCode', title: '原因代码', align: 'center' },
          { field: 'reasonDescription', title: '原因描述', align: 'center' },
          { field: 'startTime', title: '开始时间', align: 'center', width: 90 },
          { field: 'endTime', title: '结束时间', align: 'center', width: 90 }, 
          { field: 'duration', title: '持续时间', align: 'center' }
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
	    //sortName: 'taEquFaultId',           // 设置默认排序为 name
	      sortOrder: "asc",                   //排序方式
	      pagination: true,                   //是否显示分页（*）
	      search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
	      strictSearch: true,
	      showColumns: true,                  //是否显示所有的列
	      showRefresh: true,                  //是否显示刷新按钮
	      showToggle:true,                    //是否显示详细视图和列表视图
	      clickToSelect: true,                //是否启用点击选中行
	      minimumCountColumns: 2,             //最少允许的列数 clickToSelect: true, //是否启用点击选中行
	      pageNumber: 1,                      //初始化加载第一页，默认第一页
	      pageSize: 10,                    	  //每页的记录行数（*）
	      pageList: [10, 25, 50, 100],     	  //可供选择的每页的行数（*）
	      showExport: true,  				  //是否显示导出按钮  
		  exportDataType:'all', 			  //导出所有数据
	      buttonsAlign:"right",  			  //按钮位置  
	      exportTypes:['excel','csv','txt','xml','word'],  //导出文件类型  
	      Icons:'glyphicon-export',  
	      smartDisplay: true,					//智能显示分页按钮
	      paginationPreText: "上一页",
	      paginationNextText: "下一页",
	      responseHandler: responseHandler,
	      hasPreviousPage: true,
	      hasNextPage: true,
	      lastPage: true,
	      firstPage: true,
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
					zone: queryParams.zone,
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
}
