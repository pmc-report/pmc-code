$(function () {
	//初始化工厂及下级
	initShopSelected();
	//初始化时间频率
	frequencySelected();
	//初始化班次
	shiftSelected();
   
});

function initPreDownTime(params){ 
	
	var operateFormatter = function (value, row, index) {//赋予的参数
		if(value != undefined){
			var color = ' btn';
			switch(value){
				case 0 : color = 'style="background:red"';
				 break;
				case 1 : color = 'style="background:black"';
				 break;
				case 2 : color = 'style="background:yellow"';
				 break;
				case 3 : color = 'style="background:green"';
				 break;
			}
			return [ '<button type="button" class="btn btn-circle btn-lg" '+color+'></button>' ].join('');
		}
	     return '';
	}
	var  responseHandler = function(res) { // 格式化数据
        if (res.list !=null && res.list.length > 0)
            tmp = {
                total : res.totalCount,
                rows : res.list
            };
        if (res.totalCount == null)
            tmp = {
                total : '',
                rows : ''
            };
        return tmp;
    }
	
	$('#preDownTime').empty();
	$('#preDownTime').bootstrapTable('destroy').bootstrapTable({
		url: 'panel/listPrePanel', 
		method: "post",  
		dataType: "json",
		toolbar: '#toolbar',  //工具按钮用哪个容器
		striped: true,   //是否显示行间隔色
		singleSelect: false,
		//pagination: true, //分页
		//pageNumber:1,   //初始化加载第一页，默认第一页
		//pageSize: 10,   //每页的记录行数（*）
		//pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
		search: false, //显示搜索框
		showFooter: true,
		sidePagination: "server", //服务端处理分页
		responseHandler: responseHandler,
		 columns: [
				[{
	                 title : "Previous Faults Ranked by Downtime",
	                 halign : "center",
	                 align : "center",
	                 colspan : 8
		        }],
				[{
				  field: 'old',
				  title: 'Old',
				 /* footerFormatter: function (data) {
				      return "Total Downtime for All Faults";
				  }*/
				 }, {
				  field: '_new',
				  title: 'New'
				 }, {
				  field: 'occ',
				  title: 'Occ'
				 }, {
				  field: 'mins',
				  title: 'Mins',
				  /*footerFormatter: function (value) {
				      var count = 0;
				      for (var i in value) {
				          count += value[i].occ;
				      }
				      return count.toFixed(2);
					}*/
				 }, {
				  field: 'stn',
				  title: 'Stn'
				 }, {
				  field: 'description',
				  title: 'Description'
				 }, {
				  field: 'pareto',
				  title: 'Pareto'
				 }, {
				  field: 'status',
				  title: 'Status',
				  align: 'center',
		          valign: 'middle',
		          formatter: operateFormatter //自定义方法，添加操作按钮
				 }]
		],
		 queryParams : function queryParams(params) {
	            var param = {
	                pageNumber : params.pageNumber,
	                pageSize : params.pageSize
	            };
	            return param;
	        },
		 onLoadSuccess : function(data) { // 加载成功时执行
		 }
	});
}

function initOccTab(params){ 
	
	var operateFormatter = function (value, row, index) {//赋予的参数
		if(value != undefined){
			var color = ' btn';
			switch(value){
				case 0 : color = 'style="background:red"';
				 break;
				case 1 : color = 'style="background:black"';
				 break;
				case 2 : color = 'style="background:yellow"';
				 break;
				case 3 : color = 'style="background:green"';
				 break;
			}
			return [ '<button type="button" class="btn btn-circle btn-lg" '+color+'></button>' ].join('');
		}
	     return '';
	}
	var  responseHandler = function(res) { // 格式化数据
        if (res.list !=null && res.list.length > 0)
            tmp = {
                total : res.totalCount,
                rows : res.list
            };
        if (res.totalCount == null)
            tmp = {
                total : '',
                rows : ''
            };
        return tmp;
    }
	
	$('#preOcc').empty();
	$('#preOcc').bootstrapTable('destroy').bootstrapTable({
		url: 'panel/listPrePanel', 
		method: "post",  
		dataType: "json",
		toolbar: '#toolbar',  //工具按钮用哪个容器
		striped: true,   //是否显示行间隔色
		singleSelect: false,
		//pagination: true, //分页
		//pageNumber:1,   //初始化加载第一页，默认第一页
		//pageSize: 10,   //每页的记录行数（*）
		//pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
		search: false, //显示搜索框
		showFooter: true,
		sidePagination: "server", //服务端处理分页
		responseHandler: responseHandler,
		columns: [
				[{
	                 title : "Previous Faults Ranked by Occurrence",
	                 halign : "center",
	                 align : "center",
	                 colspan : 8,
		        }],
				[{
				  field: 'old',
				  title: 'Old',
				  /*footerFormatter: function (data) {
				      return "Total Occurrence for Top 10 Faults";
				  }*/
				 }, {
				  field: '_new',
				  title: 'New'
				 }, {
				  field: 'occ',
				  title: 'Occ'
				 }, {
				  field: 'mins',
				  title: 'Mins',
				  /*footerFormatter: function (value) {
			        var count = 0;
			        for (var i in value) {
			            count += value[i].occ;
			        }
			        return count.toFixed(2);
			    }*/
				 }, {
				  field: 'stn',
				  title: 'Stn'
				 }, {
				  field: 'description',
				  title: 'Description'
				 }, {
				  field: 'pareto',
				  title: 'Pareto'
				 }, {
				  field: 'status',
				  title: 'Status',
				  align: 'center',
		          valign: 'middle',
		          formatter: operateFormatter //自定义方法，添加操作按钮
				 }]
		],
		 queryParams : function queryParams(params) {
	            var param = {
	                pageNumber : params.pageNumber,
	                pageSize : params.pageSize
	            };
	            return param;
	        },
		 onLoadSuccess : function(data) { // 加载成功时执行
		 }
	});
}
function initCurrDownTime(params){ 

	var  responseHandler = function(res) { // 格式化数据
        if (res.list !=null && res.list.length > 0)
            tmp = {
                total : res.totalCount,
                rows : res.list
            };
        if (res.totalCount == null)
            tmp = {
                total : '',
                rows : ''
            };
        return tmp;
    }
	
	$('#currDownTime').empty();
	$('#currDownTime').bootstrapTable('destroy').bootstrapTable({
		url: 'panel/listCurrPanel', 
		method: "post",  
		dataType: "json",
		toolbar: '#toolbar',  //工具按钮用哪个容器
		striped: true,   //是否显示行间隔色
		singleSelect: false,
		//pagination: true, //分页
		//pageNumber:1,   //初始化加载第一页，默认第一页
		//pageSize: 10,   //每页的记录行数（*）
		//pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
		search: false, //显示搜索框
		showFooter: true,
		sidePagination: "server", //服务端处理分页
		responseHandler: responseHandler,
		 columns: [
				[{
	                 title : "Current Faults Ranked by Downtime",
	                 halign : "center",
	                 align : "center",
	                 colspan : 5
		        }],
				[{
				  field: '_new',
				  title: 'New'
				 }, {
				  field: 'occ',
				  title: 'Occ'
				 }, {
				  field: 'mins',
				  title: 'Mins',
				  /*footerFormatter: function (value) {
				      var count = 0;
				      for (var i in value) {
				          count += value[i].occ;
				      }
				      return count.toFixed(2);
					}*/
				 }, {
				  field: 'stn',
				  title: 'Stn'
				 }, {
				  field: 'description',
				  title: 'Description'
				 }]
		],
		 queryParams : function queryParams(params) {
	            var param = {
	                pageNumber : params.pageNumber,
	                pageSize : params.pageSize
	            };
	            return param;
	        },
		 onLoadSuccess : function(data) { // 加载成功时执行
		 }
	});
}

function initCurrOccTab(params){ 
	
	var  responseHandler = function(res) { // 格式化数据
        if (res.list !=null && res.list.length > 0)
            tmp = {
                total : res.totalCount,
                rows : res.list
            };
        if (res.totalCount == null)
            tmp = {
                total : '',
                rows : ''
            };
        return tmp;
    }
	
	$('#currOcc').empty();
	$('#currOcc').bootstrapTable('destroy').bootstrapTable({
		url: 'panel/listCurrPanel', 
		method: "post",  
		dataType: "json",
		toolbar: '#toolbar',  //工具按钮用哪个容器
		striped: true,   //是否显示行间隔色
		singleSelect: false,
		//pagination: true, //分页
		//pageNumber:1,   //初始化加载第一页，默认第一页
		//pageSize: 10,   //每页的记录行数（*）
		//pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
		search: false, //显示搜索框
		showFooter: true,
		sidePagination: "server", //服务端处理分页
		responseHandler: responseHandler,
		columns: [
				[{
	                 title : "Current Faults Ranked by Occurrence",
	                 halign : "center",
	                 align : "center",
	                 colspan : 5,
		        }],
				[{
				  field: '_new',
				  title: 'New'
				 }, {
				  field: 'occ',
				  title: 'Occ'
				 }, {
				  field: 'mins',
				  title: 'Mins',
				  /*footerFormatter: function (value) {
			        var count = 0;
			        for (var i in value) {
			            count += value[i].occ;
			        }
			        return count.toFixed(2);
			    }*/
				 }, {
				  field: 'stn',
				  title: 'Stn'
				 }, {
				  field: 'description',
				  title: 'Description'
				 }]
		],
		 queryParams : function queryParams(params) {
	            var param = {
	                pageNumber : params.pageNumber,
	                pageSize : params.pageSize
	            };
	            return param;
	        },
		 onLoadSuccess : function(data) { // 加载成功时执行
		 }
	});
}
function queryReport(tag,params){
	
	var url = baseURL + 'report/panel/list';
	if(tag=='9Panel'){
		
		initPreDownTime(params);
		initOccTab(params);
		initCurrDownTime(params);
		initCurrOccTab(params);
		echars(params);
		echars1(params);
		echars2(params);
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
	$.ajax({
        type: "post",
        url: baseURL + 'report/pmcopr/oprTest',
        data: '',
        dataType: "json",
        success: function(data){
        	var	option = {
					 grid:{
						   x:50,
			                y:30,
			                x2:50,
			                y2:30,
			                borderWidth:1
			            },
						title: {  
	                        //主标题文本，'\n'指定换行  
	                        text: 'Mean Time Between Failure(MTBF)',  
	                        //水平安放位置，默认为左侧，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）  
	                        x: 'left',  
	                        //垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）  
	                       y: 'top'  ,
	                       textStyle:{
	                    	   //文字颜色
	                          // color:'#ccc',
	                           //字体风格,'normal','italic','oblique'
	                          // fontStyle:'normal',
	                           //字体粗细 'normal','bold','bolder','lighter',100 | 200 | 300 | 400...
	                         //  fontWeight:'bold',
	                           //字体系列
	                           //fontFamily:'sans-serif',
	                           //字体大小
	                   　　　　			 fontSize:13
	                       },
      
	                    }, 

	                    
				    color: ['#FFA500', '#2E9AFE'],
				    tooltip: {
				        trigger: 'axis',
				        axisPointer: {
				            type: 'shadow'
				        }
				    },
				    legend: {
				    	   data: ['MTBF', 'Target MTBF'],
					       // orient: 'vertical',
					    	x:'right',
					    	y:'top',
					    	textStyle: {
					               fontSize: 8
					           }
				    
				    },
	/* 			    toolbox: {
				        show: true,
				        orient: 'vertical',
				        left: 'right',
				        top: 'center',
				        feature: {
				            mark: {show: true},
				            dataView: {show: true, readOnly: false},
				            magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
				            restore: {show: true},
				            saveAsImage: {show: true}
				        }
				    }, */
				    //calculable: true,
				    xAxis: [
				        {
				            type: 'category',
				            axisTick: {show: false},
				            axisLabel :{
				                interval:0,
				                rotate:-40
				            },
				            data: ['17/12','24/12','31/12','07/01','14/01','21/01','04/02','11/02','18/02','25/02','04/03','11/03','18/03','25/03','01/04','08/04','15/04','22/04']
				          
				        }
				    ],
				    yAxis: [
				        {
				    /*    min:'0',
				         max:'100'*/
				        }
				    ],
				    series: [
				    
				        {
				            name: 'MTBF',
				            type: 'line',
				            barGap: 0,
				            label: labelOption,
				            data: ['81.1','101.1','125.7','211','121','0','0','32.5','40','0',,'0','37.5','140','0','42','32.5','40','0','33.6','32.5','20','15']
				        },
				        {
				            name: 'Target MTBF',
				            type: 'line',
				            label: labelOption,
				            data: ['181','211','171','151','194','0','32.5','140','0','50','137.5','140','20','42','38.5','48','0','33.6','92.5','26','15']
				        },
				       
				    ]
				};
        	 //每次窗口大小改变的时候都会触发onresize事件，这个时候我们将echarts对象的尺寸赋值给窗口的大小这个属性，从而实现图表对象与窗口对象的尺寸一致的情况
	        window.onresize = echart.resize;
	        echart.setOption(option);
        }
	});
}


function echars1(params){
	var posList = [
	    'left', 'right', 'top', 'bottom',
	    'inside',
	    'insideTop', 'insideLeft', 'insideRight', 'insideBottom',
	    'insideTopLeft', 'insideTopRight', 'insideBottomLeft', 'insideBottomRight'
	];



	echart1.config = {
	    rotate: 0,
	    align: 'middle',
	    verticalAlign: 'middle',
	    position: 'top',
	    distance: 15,
	};
	var labelOption = {
	    normal: {
	        show: true,
	        position: echart1.config.position,
	        distance: echart1.config.distance,


	        fontSize: 10,
	        rich: {
	            name: {
	                textBorderColor: '#fff'
	            }
	        }
	    }
	};
	$.ajax({
        type: "post",
        url: baseURL + 'report/pmcopr/oprTest',
        data: '',
        dataType: "json",
        success: function(data){
        	var	option = {
					 grid:{
			                x:50,
			                y:30,
			                x2:50,
			                y2:30,
			                borderWidth:1
			            },
						title: {  
	                        //主标题文本，'\n'指定换行  
	                        text: 'Technical Availability Analysis(TA)',  
	                        //水平安放位置，默认为左侧，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）  
	                        x: 'left',  
	                        //垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）  
	                       y: 'top'  ,
	                       textStyle:{
	                    	   //文字颜色
	                          // color:'#ccc',
	                           //字体风格,'normal','italic','oblique'
	                          // fontStyle:'normal',
	                           //字体粗细 'normal','bold','bolder','lighter',100 | 200 | 300 | 400...
	                         //  fontWeight:'bold',
	                           //字体系列
	                           //fontFamily:'sans-serif',
	                           //字体大小
	                   　　　　			 fontSize:13
	                       },
      
	                    }, 
	                    
	                    
				    color: ['#FFA500', '#2E9AFE'],
				    tooltip: {
				        trigger: 'axis',
				        axisPointer: {
				            type: 'shadow'
				        }
				    },
				    legend: {
				        data: ['TAV', 'Target TAV'],
				       // orient: 'vertical',
				    	x:'right',
				    	y:'top',
				    	textStyle: {
				               fontSize: 8
				           }
				    
				    },
	/* 			    toolbox: {
				        show: true,
				        orient: 'vertical',
				        left: 'right',
				        top: 'center',
				        feature: {
				            mark: {show: true},
				            dataView: {show: true, readOnly: false},
				            magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
				            restore: {show: true},
				            saveAsImage: {show: true}
				        }
				    }, */
				    //calculable: true,
				    xAxis: [
				        {
				            type: 'category',
				            axisTick: {show: false},
				            axisLabel :{
				                interval:0,
				                rotate:-40
				            },
				            data: ['17/12','24/12','31/12','07/01','14/01','21/01','04/02','11/02','18/02','25/02','04/03','11/03','18/03','25/03','01/04','08/04','15/04','22/04']
				          
				        }
				    ],
				    yAxis: [
				        {
				    /*    min:'0',
				         max:'100'*/
				        }
				    ],
				    series: [
				    
				        {
				            name: 'TAV',
				            type: 'line',
				            barGap: 0,
				            label: labelOption,
				            data: ['81.1','101.1','0','211','11','181.1','101.1','115.7','201','16','231.1','141.1','115.7','211','11']
				        },
				        {
				            name: 'Target TAV',
				            type: 'line',
				            label: labelOption,
				            data: ['101','101','171','151','174','101','0','171','15','94','141','151','71','15.1','13','131','91']
				        },
				       
				    ]
				};
        	 //每次窗口大小改变的时候都会触发onresize事件，这个时候我们将echarts对象的尺寸赋值给窗口的大小这个属性，从而实现图表对象与窗口对象的尺寸一致的情况
	        window.onresize = echart1.resize;
	        echart1.setOption(option);
        }
	});
}



function echars2(params){
	var posList = [
	    'left', 'right', 'top', 'bottom',
	    'inside',
	    'insideTop', 'insideLeft', 'insideRight', 'insideBottom',
	    'insideTopLeft', 'insideTopRight', 'insideBottomLeft', 'insideBottomRight'
	];



	echart2.config = {
	    rotate: 0,
	    align: 'middle',
	    verticalAlign: 'middle',
	    position: 'top',
	    distance: 15,
	};
	var labelOption = {
	    normal: {
	        show: true,
	        position: echart2.config.position,
	        distance: echart2.config.distance,


	        fontSize: 10,
	        rich: {
	            name: {
	                textBorderColor: '#fff'
	            }
	        }
	    }
	};
	$.ajax({
        type: "post",
        url: baseURL + 'report/pmcopr/oprTest',
        data: '',
        dataType: "json",
        success: function(data){
        	var	option = {
					 grid:{
						   x:50,
			                y:30,
			                x2:50,
			                y2:30,
			                borderWidth:1
			      
			            },
						title: {  
	                        //主标题文本，'\n'指定换行  
	                        text: 'Technical Availability Trend Analysis',  
	                        //水平安放位置，默认为左侧，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）  
	                        x: 'left',  
	                        //垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）  
	                       y: 'top'  ,
	                       textStyle:{
	                    	   //文字颜色
	                          // color:'#ccc',
	                           //字体风格,'normal','italic','oblique'
	                          // fontStyle:'normal',
	                           //字体粗细 'normal','bold','bolder','lighter',100 | 200 | 300 | 400...
	                         //  fontWeight:'bold',
	                           //字体系列
	                           //fontFamily:'sans-serif',
	                           //字体大小
	                   　　　　			 fontSize:13
	                       },
      
	                    }, 

	                    
				    color: ['#FFA500', '#6fa8dc'],
				    tooltip: {
				        trigger: 'axis',
				        axisPointer: {
				            type: 'shadow'
				        }
				    },
				    legend: {
				    	   data: ['TAV', 'Target TAV'],
					       // orient: 'vertical',
				    	   textStyle: {
				               fontSize: 8
				           },

					    	x:'right',
					    	y:'top',

				    },
	/* 			    toolbox: {
				        show: true,
				        orient: 'vertical',
				        left: 'right',
				        top: 'center',
				        feature: {
				            mark: {show: true},
				            dataView: {show: true, readOnly: false},
				            magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
				            restore: {show: true},
				            saveAsImage: {show: true}
				        }
				    }, */
				    //calculable: true,
				    xAxis: [
				        {
				            type: 'category',
				            axisTick: {show: false},
				            data: ['8/11','10/11','10/11','15/11','19/11']
				          
				        }
				    ],
				    yAxis: [
				    
				        {
				    /*    min:'0',
				         max:'100'*/
	
				        }
				    ],
				    series: [
				    
				        {
				            name: 'TAV',
				            type: 'line',
				            barGap: 0,
				            label: labelOption,
				            data: ['81.1','101.1','115.7','211','111']
				        },
				        {
				            name: 'Target TAV',
				            type: 'bar',
				            label: labelOption,
				            data: ['181','211','271','100','11']
				        },
				       
				    ]
				};
        	 //每次窗口大小改变的时候都会触发onresize事件，这个时候我们将echarts对象的尺寸赋值给窗口的大小这个属性，从而实现图表对象与窗口对象的尺寸一致的情况
	        window.onresize = echart2.resize;
	        echart2.setOption(option);
        }
	});
}