$(function () {
	//初始化工厂及下级
	initShopSelected();
	//初始化时间频率
	frequencySelected();
	//初始化班次
	shiftSelected();
	$("#jqGrid").jqGrid({
        url: baseURL + 'report/pmcopr/oprTable',
        datatype: "local",
        colModel: [	
        	{ label: 'Date', name: 'workingDay', index: 'workingDay', width: 50 },
        	{ label: 'OPR Target', name: 'oprTarget', index: 'oprTarget', width: 50 },
			{ label: 'E-OPR', name: 'eopr', index: 'eopr', width: 50 },
			{ label: 'Production Volume', name: 'productionVolume', index: 'productionVolume', width: 50 },
			{ label: 'Available Time', name: 'availableTime', index: 'availableTime', width: 50 },
			{ label: 'P-OPR', name: 'popr', index: 'popr', width: 50 }
						
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: false,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order:"order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
       
    })
});

function queryReport(tag,params){
	$("#jqGrid").jqGrid('clearGridData');  //清空表格
	$("#jqGrid").jqGrid('setGridParam',{  // 重新加载数据
		   url: baseURL + 'report/pmcopr/oprTable',
	       datatype:'json',
	       mtype: 'post',
	       postData : {
	        	shop : params.shop,
	        	area : params.area,
	        	zone : params.zone,
	        	line : params.line,
	        	shift : params.shift,
	        	startTime : params.startTime,
	        	endTime : params.endTime,
	        	frequency : params.frequency,
	        	
	        }
	      
	}).trigger("reloadGrid");
	
	
	
	$.ajax({
        type: "post",
        url: baseURL + 'report/pmcopr/oprTest',
        data: '',
        dataType: "json",
        success: function(data){
        	var	option = {
					 grid:{
			                x:200,
			                y:50,
			                x2:200,
			                y2:40,
			                borderWidth:1
			            },
						title: {  
	                        //主标题文本，'\n'指定换行  
	                        text: 'OPR Report',  
	                        //水平安放位置，默认为左侧，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）  
	                        x: 'center',  
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
	                   　　　　			 fontSize:30
	                       }
	                    }, 
				    color: ['#708090', '#36648B', '#CD3700'],
				    tooltip: {
				        trigger: 'axis',
				        axisPointer: {
				            type: 'shadow'
				        }
				    },
				    legend: {
				        data: ['E-OPR-value', 'P-OPR-value', 'OPR Target-value'],
				        orient: 'vertical',
				    	x:'right',
				    	y:'middle',
				    
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
				    calculable: true,
				    xAxis: [
				        {
				            type: 'category',
				            axisTick: {show: false},
				            data: data.date.date
				          
				        }
				    ],
				    yAxis: [
				        {
				        min:'0',
				         max:'100'
				        }
				    ],
				    series: [
				    
				        {
				            name: 'E-OPR-value',
				            type: 'bar',
				            barGap: 0,
				            label: labelOption,
				            data: data.date.E
				        },
				        {
				            name: 'P-OPR-value',
				            type: 'bar',
				            label: labelOption,
				            data: data.date.P
				        },
				        {
				            name: 'OPR Target-value',
				            type: 'line',
				           // label: labelOption,
				            data: data.date.O
				        },
				       
				    ]
				};
		        //每次窗口大小改变的时候都会触发onresize事件，这个时候我们将echarts对象的尺寸赋值给窗口的大小这个属性，从而实现图表对象与窗口对象的尺寸一致的情况
		        window.onresize = echart.resize;
		        echart.setOption(option);
                 }
    });
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
function imexport(){
	var tableid = "jqGrid";
	var dd = $("#gbox_"+tableid +' .ui-jqgrid-htable thead');
	var ee = $('#'+tableid );
	ee.find('.jqgfirstrow').remove();//干掉多余的无效行
	ee.find('tbody').before(dd);//合并表头和表数据
	ee.find('tr.ui-search-toolbar').remove();//干掉搜索框
	ee.tableExport({
		type: 'excel',
		escape: 'false'
	});
	var a = $("#"+tableid ).find('thead');//把合并后的表头和数据拆分
	$("#gbox_"+tableid +' .ui-jqgrid-htable').append(a);
}