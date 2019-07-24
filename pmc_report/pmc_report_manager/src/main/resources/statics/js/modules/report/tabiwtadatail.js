$(function () {
	//初始化工厂及下级
	initShopSelected();	
	//班次下拉框
	shiftSelected();
	
	//初始化查询条件展示栏
	inittadtableTitle();
	
	//初始化MS01表格
	inittadms01Table();
	//初始化Faults表格
	inittadfaultsTable();
	selectChange();
});

function inittadtableTitle(params) {
	$('#tadTableTitleHeader').empty();   //每次变化时清空所有子节点
	var table = '';
	var tabletdf = '<tbody>';
	var tablebody = '<tr>'
			+ '<td>车间</td>'
			+ '<td></td>'
			+ '<td>区域</td>'
			+ '<td></td>'
			+ '<td>Zone</td>'
			+ '<td></td>'
			+ '<td>时间范围从</td>'
			+ '<td></td>'
			+ '<td></td>'
			+ '<td></td>'
			+ '</tr>'
			
			+ '<tr>'
			+ '<td>班次</td>'
			+ '<td></td>'
			+ '<td>车型</td>'
			+ '<td></td>'
			+ '<td></td>'
			+ '<td></td>'
			+ '<td>到</td>'
			+ '<td></td>'
			+ '<td>报表生成时间</td>'
			+ '<td></td>'
			+ '</tr>'
	var tabletde = '</tbody>';
	table += (tabletdf + tablebody + tabletde);
	$('#tadTableTitleHeader').html(table);
	$('#tadTableTitleHeader td:nth-child(1)').css("border-left","1px solid #000");
	var lengths = $('#tadTableTitleHeader tr:eq(0)').children('td').length;
	for(var i = 1 ; i <=lengths;i++){
		if(i%2 != 0){
			$('#tadTableTitleHeader td:nth-child('+i+')').css({"font-weight":"bold","width":"6%","text-align":"center"});
		}else{
			$('#tadTableTitleHeader td:nth-child('+i+')').css({"width":"14%","text-align":"center"});
			$('#tadTableTitleHeader td:nth-child('+i+')').html("All");
		}
	}
	$('#tadTableTitleHeader tr:eq(0) td:last-child').html('');
	$('#tadTableTitleHeader tr:eq(1) td:eq(5)').html('');
	$('#tadTableTitleHeader tr:eq(1) td:last-child').html('');
}

function queryReport(tag,params){
	
	var url = baseURL + 'report/opr/list';
	if(tag=='TADOPR'){
		
		inittadtableTitle(params);
		inittadms01Table(url,params);
		inittadfaultsTable(url,params);
	}
}

function inittadms01Table(url,queryParams){
	
   var columns = [
        { field: 'date', title: 'Date', align: 'center'},
        { field: 'targetTa', title: 'Target TA', align: 'center'}, 
        { field: 'ta', title: 'TA', align: 'center',},
        { field: 'goodPartCount', title: 'Good Part Count', align:'center'}, 
        { field: 'downTime', title: 'Downtime', align: 'center'}, 
        { field: 'occ', title: 'Occ', align: 'center'},
        { field: 'buildTime', title: 'Build Time', align: 'center'}
    ];
   
	  $('#tad_MS01').empty();
	  $('#tad_MS01').bootstrapTable('destroy').bootstrapTable({
	      url: url,   						  //url一般是请求后台的url地址,调用ajax获取数据。此处我用本地的json数据来填充表格。
	      method: "post",                     //使用get请求到服务器获取数据
	      dataType: "json",
	      contentType: "application/x-www-form-urlencoded",
	      toolbar: "#toolbar",                //一个jQuery 选择器，指明自定义的toolbar 例如:#toolbar, .toolbar.
	    //uniqueId: 'taEquFaultId',           //每一行的唯一标识，一般为主键列						  //document.body.clientHeight-165  //动态获取高度值，可以使表格自适应页面
	      cache: false,                       // 不缓存
	      striped: true,                      // 隔行加亮
	      queryParamsType: '',           	  //设置为"undefined",可以获取pageNumber，pageSize，searchText，sortName，sortOrder 
	                                          //设置为"limit",符合 RESTFul 格式的参数,可以获取limit, offset, search, sort, order 
	      sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
	      sortable: true,                     //是否启用排序;意味着整个表格都会排序
	    //sortName: 'taEquFaultId',           // 设置默认排序为 name
	      sortOrder: "asc",                   //排序方式
	     // pagination: true,                   //是否显示分页（*）
	      search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
	      strictSearch: true,
	      //showColumns: true,                  //是否显示所有的列
	      //showRefresh: true,                  //是否显示刷新按钮
	      //showToggle:true,                    //是否显示详细视图和列表视图
	      clickToSelect: true,                //是否启用点击选中行
	      minimumCountColumns: 2,             //最少允许的列数 clickToSelect: true, //是否启用点击选中行
	      pageNumber: 1,                      //初始化加载第一页，默认第一页
	      pageSize: 10,                    	  //每页的记录行数（*）
	      pageList: [10, 25, 50, 100],     	  //可供选择的每页的行数（*）
	      //showExport: true,  				  //是否显示导出按钮  
		  exportDataType:'all', 			  //导出所有数据
	      buttonsAlign:"right",  			  //按钮位置  
	      exportTypes:['excel','csv','txt','xml','word'],  //导出文件类型  
	      Icons:'glyphicon-export',  
	     // smartDisplay: true,					//智能显示分页按钮
	     // paginationPreText: "上一页",
	     // paginationNextText: "下一页",
//	      responseHandler: responseHandler,
	      hasPreviousPage: true,
	     // hasNextPage: true,
	    //  lastPage: true,
	    //  firstPage: true,
	      columns: columns,
	      queryParams : function(params) {
		      return {
	            	area : queryParams.area,
					zone : queryParams.zone,
					eTime: queryParams.eTime,
					sTime: queryParams.sTime,
					shift: queryParams.shift,
					shop: queryParams.shop,
					jobId : queryParams.jobId,
		      	}
		  },
//	      exportOptions : {  
//	          ignoreColumn: [0],  				//忽略某一列的索引  
//	          fileName: '区域OPR报表',  			//文件名称设置  
//	          worksheetName: '区域 OPR',  			//表格工作区名称  
//	          tableName: '区域OPR报表',  
//	          excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],  
//	          onMsoNumberFormat: function DoOnMsoNumberFormat(cell, row, col) {  
//					               var result = "";  
//					               if (row > 0 && col == 0)  
//					                   result = "\\@";  
//					               return result;  
//	           		}    
//	      }, 
	      onLoadSuccess: function (data) { 		//加载成功时执行
	          //console.log(data);
	      },
	      onLoadError: function (res) { 		//加载失败时执行
	          //console.log(res);
	      }
	  });
}

function inittadfaultsTable(url,queryParams){
	
	   var columns = [
	        { field: 'occ2', title: 'Occ', align: 'center'},
	        { field: 'minutes', title: 'Minutes', align: 'center',}, 
	        { field: 'station', title: 'Station', align: 'center',},
	        { field: 'description', title: 'Description', align:'center',}, 
	    ];
	   
		  $('#tad_Faults').empty();
		  $('#tad_Faults').bootstrapTable('destroy').bootstrapTable({
		      url: url,   						  //url一般是请求后台的url地址,调用ajax获取数据。此处我用本地的json数据来填充表格。
		      method: "post",                     //使用get请求到服务器获取数据
		      dataType: "json",
		      contentType: "application/x-www-form-urlencoded",
		      toolbar: "#toolbar",                //一个jQuery 选择器，指明自定义的toolbar 例如:#toolbar, .toolbar.
		    //uniqueId: 'taEquFaultId',           //每一行的唯一标识，一般为主键列						  //document.body.clientHeight-165  //动态获取高度值，可以使表格自适应页面
		      cache: false,                       // 不缓存
		      striped: true,                      // 隔行加亮
		      queryParamsType: '',           	  //设置为"undefined",可以获取pageNumber，pageSize，searchText，sortName，sortOrder 
		                                          //设置为"limit",符合 RESTFul 格式的参数,可以获取limit, offset, search, sort, order 
		      sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
		      sortable: true,                     //是否启用排序;意味着整个表格都会排序
		    //sortName: 'taEquFaultId',           // 设置默认排序为 name
		      sortOrder: "asc",                   //排序方式
		     // pagination: true,                   //是否显示分页（*）
		      search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
		      strictSearch: true,
		      //showColumns: true,                  //是否显示所有的列
		      //showRefresh: true,                  //是否显示刷新按钮
		      //showToggle:true,                    //是否显示详细视图和列表视图
		      clickToSelect: true,                //是否启用点击选中行
		      minimumCountColumns: 2,             //最少允许的列数 clickToSelect: true, //是否启用点击选中行
		      pageNumber: 1,                      //初始化加载第一页，默认第一页
		      pageSize: 10,                    	  //每页的记录行数（*）
		      pageList: [10, 25, 50, 100],     	  //可供选择的每页的行数（*）
		      //showExport: true,  				  //是否显示导出按钮  
			  exportDataType:'all', 			  //导出所有数据
		      buttonsAlign:"right",  			  //按钮位置  
		      exportTypes:['excel','csv','txt','xml','word'],  //导出文件类型  
		      Icons:'glyphicon-export',  
		     // smartDisplay: true,					//智能显示分页按钮
		     // paginationPreText: "上一页",
		     // paginationNextText: "下一页",
//		      responseHandler: responseHandler,
		      hasPreviousPage: true,
		     // hasNextPage: true,
		    //  lastPage: true,
		    //  firstPage: true,
		      columns: columns,
		      queryParams : function(params) {
			      return {
		            	area : queryParams.area,
						zone : queryParams.zone,
						eTime: queryParams.eTime,
						sTime: queryParams.sTime,
						shift: queryParams.shift,
						shop: queryParams.shop,
						jobId : queryParams.jobId,
			      	}
			  },
//		      exportOptions : {  
//		          ignoreColumn: [0],  				//忽略某一列的索引  
//		          fileName: '区域OPR报表',  			//文件名称设置  
//		          worksheetName: '区域 OPR',  			//表格工作区名称  
//		          tableName: '区域OPR报表',  
//		          excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],  
//		          onMsoNumberFormat: function DoOnMsoNumberFormat(cell, row, col) {  
//						               var result = "";  
//						               if (row > 0 && col == 0)  
//						                   result = "\\@";  
//						               return result;  
//		           		}    
//		      }, 
		      onLoadSuccess: function (data) { 		//加载成功时执行
		          //console.log(data);
		      },
		      onLoadError: function (res) { 		//加载失败时执行
		          //console.log(res);
		      }
		  });
	}
