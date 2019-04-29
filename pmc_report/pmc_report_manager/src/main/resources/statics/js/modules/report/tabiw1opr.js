$(function () {
	//初始化工厂及下级
	initShopSelected();	
	//初始化班次
	shiftSelected();
	
	initTable();
});

function queryReport(tag,params){
	
	var url = baseURL + 'report/pmcopr/biw1opr';
	if(tag=='Biw1OPR'){
		echars(params);
		initTable(url,params);
	}
}

function echars(params){
	
	var posList = [
	    'left', 'right', 'top', 'bottom',
	    'inside',
	    'insideTop', 'insideLeft', 'insideRight', 'insideBottom',
	    'insideTopLeft', 'insideTopRight', 'insideBottomLeft', 'insideBottomRight'
	];

	echart.config = {
	    rotate: 0,
	    align: 'middle',
	    verticalAlign: 'middle',
	    position: 'top',
	    distance: 15,
	};
	var labelOption = {
	    normal: {
	        show: true,
	        position: echart.config.position,
	        distance: echart.config.distance,

	        fontSize: 10,
	        rich: {
	            name: {
	                textBorderColor: '#fff'
	            	}
	        	}
	    	}
	};
	
	var date = [];
	    var mtbf = [];
	    var tarMtbf = [];
	
	var	option = {
			 grid:{
				    x:50,
	                y:30,
	                x2:50,
	                y2:30,
	                borderWidth:1,
	            },
				title: {  
                    //主标题文本，'\n'指定换行  
                    text: 'Mean Time Between Failure(MTBF)',  
                    //水平安放位置，默认为左侧，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）  
                    x: 'left',  
                    //垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）  
                   y: 'top'  ,
                   textStyle:{
               　　　　			 fontSize:13
                   },
                }, 

		    color: ['#FFA500', '#2E9AFE'],
		    //calculable: true,
		    xAxis: [
		        {
		            type: 'category',
		            axisTick: {show: true},
		            axisLabel :{
		                interval:0,
		                //rotate:45,   //倾斜度
		                fontSize:10
		            },
		            data: date
		        }
		    ],
		  
		    yAxis: [  {}],
		    series: [
		        {
		            name: 'MTBF',
		            type: 'line',
		            barGap: 0,
		            label: labelOption,
		            data: mtbf,
		            hoverAnimation:false,//禁用鼠标悬停弹出效果
		        },
		        {
		            name: 'Target MTBF',
		            type: 'line',
		            label: labelOption,
		            data: mtbf,
		            hoverAnimation:false,
		        },
		    ]
		};
	 //每次窗口大小改变的时候都会触发onresize事件，这个时候我们将echarts对象的尺寸赋值给窗口的大小这个属性，从而实现图表对象与窗口对象的尺寸一致的情况
    window.onresize = echart.resize;
    echart.setOption(option);
    
	/*$.ajax({
        type: "post",
        url: '',
        data: '',
        dataType: "json",
        success: function(data){
        }
	});*/
}

function initTable(url,queryParams){
	
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
	 
	 var dateFormatter = function(value, row, index){
 	    return sec_to_time(value);
     } 
	 
     var columns = [
          
          { field: 'shop', title: '区域', align: 'center', sortable:false },
          { field: 'productionVolume', title: 'Zone', align: 'center', sortable:false }, 
          { field: 'frequency', title: 'frequency', align: 'center', sortable:false }, 
          { field: 'tarOpr', title: 'tarOpr', align: 'center', sortable: true, clickToSelect: false, sortName: "facilityId", order:"asc" },
          { field: 'productionOpr', title: 'productionOpr', halign:'center' }, 
      ];
     
	  $('#biw10prtab').empty();
	  $('#biw10prtab').bootstrapTable('destroy').bootstrapTable({
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



