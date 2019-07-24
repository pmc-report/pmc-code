$(function () {
	//初始化工厂及下级
	initShopSelected();
	//初始化班次
	initLossTableTitle();
	shiftSelected();
});

//preDownTime
//用于存放parato-->Img
var tableDownTimeparetoImgArray = new Array();
//用于存放status-->Img
var tableDownTimestatusImgArray = new Array();

//preOcc
//用于存放parato-->Img
var tableOccparetoImgArray = new Array();
//用于存放status-->Img
var tableOccstatusImgArray = new Array();

function initLossTableTitle(params){

	$('#lossTableHeader').empty();   //每次变化时清空所有子节点
	var table = '';
	var tabletdf = '<tbody>';
	var tablebody = '<tr>'
			+ '<td>车间</td>'
			+ '<td></td>'
			+ '<td>区域</td>'
			+ '<td></td>'
			+ '<td>Zone</td>'
			+ '<td></td>'
			+ '</tr>'
			
			+ '<tr>'
			+ '<td>班次</td>'
			+ '<td></td>'
			+ '<td></td>'
			+ '<td></td>'
			+ '<td>报表生成时间</td>'
			+ '<td></td>'
			+ '</tr>'
	var tabletde = '</tbody>';
	table += (tabletdf + tablebody + tabletde);
	$('#lossTableHeader').html(table);
//	console.log($('#equFaultTableHeader tr:eq(0)').children('td').length);   获取首个tr下td 的个数
	var lengths = $('#lossTableHeader tr:eq(0)').children('td').length;
	for(var i = 1 ; i <=lengths;i++){
		if(i%2 != 0){
			$('#lossTableHeader td:nth-child('+i+')').css({"font-weight":"bold","width":"7%","text-align":"center"});
		}else{
			$('#lossTableHeader td:nth-child('+i+')').html("All");
			$('#lossTableHeader td:nth-child('+i+')').css("width","23%");
		}
	}
	$('#lossTableHeader td:nth-child(1)').css("border-left","1px solid #000");
	$('#lossTableHeader tr:eq(1) td:eq(3)').html('');
	$('#lossTableHeader tr:last-child td:last-child').html('');
	
	if(params != null && params != ''){
		console.log(params)
		if(params.shop != null && params.shop.trim() != ''){
			$('#lossTableHeader tr:eq(0) td:eq(1)').html(params.shop);
		}else{
			$('#lossTableHeader tr:eq(0) td:eq(1)').html("All");
		}
		
		if(params.area != null && params.area.trim() != ''){
			$('#lossTableHeader tr:eq(0) td:eq(3)').html(params.area);
		}else{
			$('#lossTableHeader tr:eq(0) td:eq(3)').html("All");
		}
		
		if(params.zone != null && params.zone.trim() != ''){
			$('#lossTableHeader tr:eq(0) td:eq(5)').html(params.zone);
		}else{
			$('#lossTableHeader tr:eq(0) td:eq(5)').html("All");
		}
		
		if(params.shift != null && params.shift.trim() != ''){
			$('#lossTableHeader tr:eq(1) td:eq(1)').html(params.shift);
		}else{
			$('#lossTableHeader tr:eq(1) td:eq(1)').html("All");
		}

		$('#lossTableHeader tr:eq(1) td:eq(3)').html("");
		var mydate = new Date();
		var createTime = mydate.getFullYear() + '-'+ Appendzero(mydate.getMonth()+1) + '-' + Appendzero(mydate.getDate()) +'  '+mydate.getHours() + ':' + Appendzero(mydate.getMinutes())+':'+Appendzero(mydate.getSeconds());
		$('#lossTableHeader tr:last-child td:last-child').html(createTime);
	}

}

function Appendzero(obj){
	if (obj < 10) {
		return "0" + "" + obj;
	} else {
		return obj;
	}
}

function initLossTable(queryParams){ 
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
     var columns = [
    	 [{
         title : "<span>Year </span></br><span>Days:"+fdates+"</span>",
         halign : "center",
         align : "left",
         colspan : colls
    	 }],[
    	  {title: '序号', align: 'center', formatter: function indexFormatter(value, row, index) {return index + 1;}},
          { field: 'line', title: '区域', align: 'center', sortable:false },
          { field: 'zone', title: 'Zone', align: 'center', sortable:false }, 
          { field: 'station', title: '工位', align: 'center', sortable:false }, 
          { field: 'facilityId', title: '设备号', align: 'center'},
          { field: 'facilityDesc', title: '设备名称', halign:'center' }, 
          { field: 'jobId', title: '车型', align: 'center'}, 
          { field: 'faultWord1', title: 'fault_Word1', align: 'center' },
          { field: 'faultWord2', title: 'fault_Word2', align: 'center' }, 
          { field: 'faultWord3', title: 'fault_Word3', align: 'center' }, 
          { field: 'posWord31', title: 'Word31', align: 'center',
        	  formatter : function replaceFormatter(value, row, index) {
        		  if(value == null){
        			  return "0"
        		  }
        		  return value;
        	  } 
          },
          { field: 'faultDescription', title: '故障描述', align: 'center' }, 
          { field: 'reasonCode', title: '原因代码', align: 'center' },
          { field: 'reasonDescription', title: '原因描述', align: 'center' },
          { field: 'startTime', title: '开始时间', align: 'center', width: 90 },
          { field: 'endTime', title: '结束时间', align: 'center', width: 90 }, 
          { field: 'duration', title: '持续时间', align: 'center',formatter: dateFormatter}
      ]
     ];
     
	  $('#faultLossTable').empty();
	  $('#faultLossTable').bootstrapTable('destroy').bootstrapTable({
	      url: url,   						  //url一般是请求后台的url地址,调用ajax获取数据。此处我用本地的json数据来填充表格。
	      method: "post",                     //使用get请求到服务器获取数据
	      dataType: "json",
	      contentType: "application/x-www-form-urlencoded",
	      //toolbar: "#toolbar",                //一个jQuery 选择器，指明自定义的toolbar 例如:#toolbar, .toolbar.
	    //uniqueId: 'taEquFaultId',           //每一行的唯一标识，一般为主键列
	      height: 522,						  //document.body.clientHeight-165  //动态获取高度值，可以使表格自适应页面
	      cache: false,                       // 不缓存
	      striped: true,                      // 隔行加亮
	      queryParamsType: '',           	  //设置为"undefined",可以获取pageNumber，pageSize，searchText，sortName，sortOrder 
	                                          //设置为"limit",符合 RESTFul 格式的参数,可以获取limit, offset, search, sort, order 
	      sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）                 //是否启用排序;意味着整个表格都会排序         // 设置默认排序为 name
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
	      pageSize: 100,                    	  //每页的记录行数（*）
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
					shift: queryParams.shift,
					shop: queryParams.shop
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
}

function createDownTimeExportImg(){
	var tableDownTimetrleg = $('#preDownTime tbody').find('tr').length;
	var tableDownTimetdleg = $('#preDownTime tbody').find('tr').eq(0).find('td').length;
	for(var i = 0 ; i <tableDownTimetrleg;i++){
		for(var j = 0;j<tableDownTimetdleg;j++){
			if(j == 6 || j == 7){
				if( j == 6){
					createDownTimeDivtoCanvas(i,j)
				}else if(j == 7){
					createDownTimeDivtoCanvas(i,j);
				}
			}
		}
	}
}

function createDownTimeDivtoCanvas(i,j){
	setTimeout(function(){
		var _canvas='';
		if(j == 6){
			_canvas = document.querySelector('.paretoDownTime_'+i);
		}
		if(j == 7){
			_canvas = document.querySelector('.statusDownTime_'+i);
		}
		var w = parseInt(window.getComputedStyle(_canvas).width);
		var h = parseInt(window.getComputedStyle(_canvas).height);
	 	// 导出宽度
		var width = w * 2;
		// 导出高度
		var height = h * 2;
		domtoimage.toPng(_canvas).then(function (dataUrl) {
			    var img = new Image();
			    img.width = width;
			    img.height = height;
			    img.style.width = w + "px";
			    img.style.height = h + "px";
			    img.src = dataUrl;
			    //document.body.appendChild(img);
			    if(j == 6){
			    	tableDownTimeparetoImgArray[i] = dataUrl;
				}
				if(j == 7){
					tableDownTimestatusImgArray[i] = dataUrl;
				}
		})
		.catch(function (error) {
		    console.error('图片转换异常!', error);
		});
		setPorpById('panelBtn','disabled',false);
	},5000);
}
function initOccTab(queryParams){ 
	var fdates = queryParams.fDate;
	var tdates = queryParams.tDate;
	var occ = '';
	var mins = '';
	var totalOcc1 = '';
	var totalOcc2 = '';
	var operateFormatter = function (value, row, index) {//赋予的参数
		if(value != undefined){
			var color = '';
			switch(value){
				case 0 : color = 'style="background:red;border:1px solid #666;border-radius:50%;box-shadow:0 0 1px 1px; padding:7px;outline:none;"';
				 break;
				case 1 : color = 'style="background:black;border:1px solid #666;border-radius:50%;box-shadow:0 0 1px 1px; padding:7px;outline:none;"';
				 break;
				case 2 : color = 'style="background:yellow;border:1px solid #666;border-radius:50%;box-shadow:0 0 1px 1px;padding:7px;outline:none;"';
				 break;
				case 3 : color = 'style="background:green;border:1px solid #666;border-radius:50%;box-shadow:0 0 1px 1px;padding:7px;outline:none;"';
				 break;
				case 4 : color = 'style = "background:#ffffff;border:1px solid #666;border-radius:50%;box-shadow:0 0 1px 1px;padding:7px;outline:none;"';
				 break;
			}
			return [ '<div class = "statusOcc_'+index+'"><button type="button" class="btn btn-circle btn-lg" '+color+'></button></div>' ].join('');
		}
	     return '';
	}
	var  responseHandler = function(res) { // 格式化数据
		
		if (res.preOccurrenceList !=null && res.preOccurrenceList.length > 0){
            tmp = {
                total : res.totalCount,
                rows : res.preOccurrenceList
            };
            
            var list = res.preOccurrenceList;
            for(var i=0;i<list.length;i++ ){
         	   if(i==0
         			   &&list[i].totalOcc1!=null
         			   &&list[i].totalOcc2!=null){
	         	   totalOcc1 = list[i].totalOcc1;
	         	   totalOcc2 = list[i].totalOcc2;
         		   $('#totalOccurrenceOld').html(totalOcc1);
         		   $('#totalOccurrenceNew').html(totalOcc2);
         	   }
            }
       
         return tmp;
        }
        if(res.totalCount==null){
        	 tmp = {
                     total : '',
                     rows : ''
                 };
	        $('#totalOccurrenceOld').html('0');
	   		$('#totalOccurrenceNew').html('0');
        	 return tmp;
        }
    
    }
	
	var fontFormatter = function(value,row,index) {
		var dom = '';
		if(value!=null){
			dom = '<span style="font-size:12px">'+value+'</span>';
		}else{
			dom = '<span style="font-size:12px"> </span>';
		}
          
     return dom;  
	}
	
	//获取故障次数
	var getOcc = function(value,row,index){
		if(value!=null){
			occ = value;
		}
		return occ;
	}
	
	//获取故障时间
	var getMins = function(value,row,index){
		if(value!=null){
			mins = value;
		}
		return mins;
	}
	
	//获取故障次数
	var getOccOld = function(value,row,index){
		if(value!=null){
		  occ1 = value;
		  var count1 = 0;
	      for (var i in value) {
	          count1 += value[i].occ1;
	      }
	      //开始时间表前十故障次数显示在页脚
	      $('#top10OccurrenceOld').html(count1);
		}
		return occ1;
	}
	
	//获取累加故次数续时间
	var getOccNew = function(value,row,index){
		if(value!=null){
			occ2 = value;
			 var count2 = 0;
		      for (var i in value) {
		          count2 += value[i].occ2;
		      }
		}
		//结束时间表前十故障次数显示在页脚
	    $('#top10OccurrenceNew').html(count2);
		return occ2;
	}
	
	//根据次数和持续时间动态展示Pareto
	var paretoFormatter = function(value,row,index){
		if(mins!=null){
			var width1 = occ>100?100:occ;
			var width2 = mins>100?100:mins;
			var dom =  '<div class= "paretoOcc_'+index+'"><input type="text" disabled="disabled" style="background-color: yellow;border: none;height: 12px;width: '+
			width1 +'px"><span style="font-size:12px">'+ occ +'</span></input></br><input type="text" disabled="disabled" style="background-color: green;border: none;height: 12px;width: '+ 
			width2.toFixed(1) +'%"><span style="font-size:12px">'+ mins.toFixed(2) +'</span></input></div>'
			
			return dom;
		}
	}
	
	var borderFormatter =  function(value,row,index){
		return {css:{"border-right-style":"double"}}
	}
	
	$('#preOcc').empty();
	$('#preOcc').bootstrapTable('destroy').bootstrapTable({
		url: 'panel/listCurrPanel', 
		method: "post",                     //使用get请求到服务器获取数据
	    dataType: "json",
	    contentType: "application/x-www-form-urlencoded",
		queryParamsType: '', 				//参数格式,发送标准的RESTFul类型的参数请求 
		search: false, 						//显示搜索框
		showFooter: true,  					//显示底部栏
		sidePagination: "server", 			//服务端处理分页
		responseHandler: responseHandler,
		columns: [
				[{
	                 title : "<span>Previous Faults Ranked by Occurrence</span></br><span>Days:"+fdates+"</span>",
	                 halign : "center",
	                 align : "center",
	                 colspan : 8
		        },{
	                 title : "<span>Root Cause Analysis</span></br><span>[Ranked by Occurence]</span>",
	                 halign : "center",
	                 align : "center",
	                 colspan : 4
		        },{
	                 title : "<span>Current Faults Ranked by Occurrence</span></br><span>Days:"+tdates+"</span>",
	                 halign : "center",
	                 align : "center",
	                 colspan : 5
		        }],
				[{
				  field: 'old',
				  title: 'Old',
				  formatter: fontFormatter,
				 }, {
				  field: 'oldThenNew',
				  title: 'New',
				  formatter: fontFormatter,
				 }, {
				  field: 'occ1',
				  title: 'Occ',
				  cellStyle: getOcc,
				  footerFormatter: getOccOld,
				  formatter: fontFormatter,
				 }, {
				  field: 'mins1',
				  title: 'Mins',
				  cellStyle: getMins,
				  formatter: fontFormatter,
				 }, {
				  field: 'stn1',
				  title: 'Stn',
				  formatter: fontFormatter,
				  //footerFormatter: getTop10DownTime
				 }, {
				  field: 'description1',
				  title: 'Description',
				  formatter: fontFormatter,
				 }, {
				  field: 'pareto',
				  title: 'Pareto',
				  formatter: paretoFormatter,
				 }, {
				  field: 'status',
				  title: 'Status',
				  align: 'center',
		          valign: 'middle',
		          cellStyle: borderFormatter,
		          formatter: operateFormatter, //自定义方法，添加操作按钮
				 },{
				  field: 'casualDescription',
				  title: 'Casual Description',
				 }, {
				  field: 'rootCauseAnalysis',
				  title: 'Root Cause Analysis',
				 }, {
				  field: 'who',
				  title: 'Who',
				 }, {
				  field: 'timing',
				  title: 'Timing',
				  cellStyle: borderFormatter
				 },{
				  field: '_new',
				  title: 'New',
				  formatter: fontFormatter,
				 }, {
				  field: 'occ2',
				  title: 'Occ',
				  footerFormatter: getOccNew,
				  formatter: fontFormatter,
				 }, {
				  field: 'mins2',
				  title: 'Mins',
				  formatter: fontFormatter
				 }, {
				  field: 'stn2',
				  title: 'Stn',
				  formatter: fontFormatter
				 }, {
				  field: 'description2',
				  title: 'Description',
				  formatter: fontFormatter
				 }]
		],
		queryParams : function(params) {
		      return {
	                area : queryParams.area,
					zone : queryParams.zone,
					eTime: queryParams.eTime,
					sTime: queryParams.sTime,
					shift: queryParams.shift,
					shop: queryParams.shop,
					jobId : queryParams.jobId,
					fromDate : queryParams.fromDates,
					toDate : queryParams.toDates
	            }
	     },
		/* onLoadSuccess: function (data) { 		//加载成功时执行
			 $('#preOccFoot').show();
			 createOccExportImg();
			 setPorpById('panelBtn','disabled',false);
		 },*/
		 onPostBody : function (data){
			 $('#preOccFoot').show();
			 createOccExportImg();
		 },
	     onLoadError: function (res) { 			//加载失败时执行
	     }
	});
}

function createOccExportImg(){
	var tableOccparamstrleg = $('#preOcc tbody').find('tr').length;
	var tableOccparamstdleg = $('#preOcc tbody').find('tr').eq(0).find('td').length;
	var node = '';
	for(var i = 0 ; i <tableOccparamstrleg;i++){
		for(var j = 0;j<tableOccparamstdleg;j++){
			if(j == 6 || j == 7){
				if(j == 6){
					createOccDivtoCanvas(i,j)
				} else if(j == 7){
					createOccDivtoCanvas(i,j);
				}
			}
		}
	}
}

function createOccDivtoCanvas(i,j){
	var _canvas='';
	if(j == 6){
		_canvas = document.querySelector('.paretoOcc_'+i);
	}
	if(j == 7){
		_canvas = document.querySelector('.statusOcc_'+i);
	}
	var w = parseInt(window.getComputedStyle(_canvas).width);
	var h = parseInt(window.getComputedStyle(_canvas).height);
 	// 导出宽度
	var width = w * 2;
	// 导出高度
	var height = h * 2;
	domtoimage.toPng(_canvas).then(function (dataUrl) {
		    var img = new Image();
		    img.width = width;
		    img.height = height;
		    img.style.width = w + "px";
		    img.style.height = h + "px";
		    img.src = dataUrl;
		    //document.body.appendChild(img);
		    if(j == 6){
				tableOccparetoImgArray[i] = dataUrl;
			}
			if(j == 7){
				tableOccstatusImgArray[i] = dataUrl;
			}
	})
	.catch(function (error) {
	    console.error('图片转换异常!', error);
	});
}

function queryReport(tag,params){
	if(isNullOrBlank(params.area)){
		alert("请选择查询区域");
    	return ;
	}
	if(isNullOrBlank(params.zone)){
		alert("请选择查询Zone");
    	return ;
	}
	if(isNullOrBlank(params.shift)){
		alert("请选择查询班次");
    	return ;
	}
	if(tag=='faultLoss'){
		
		echars(params);
		echars1(params);
		echars2(params);
		
		initLossTableTitle(params)
		//initOccTab(params);
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
        url: baseURL + 'report/loss/oprLoss',
        data: params,
        dataType: "json",
        success: function(data){
        	var date = [];
      	    var actualEOprArr = [];
      	    var actualPOprArr = [];
    	    var targetEOprArr = [];
    	    var targetPOprArr = [];
      	    if(data.oprLoss!=null&&data.oprLoss.length>0){
      	    	for(var i=0;i<data.oprLoss.length;i++){
          	    	date.push(data.oprLoss[i].monday);
          	    	data.oprLoss[i].actualPOpr==0?actualPOprArr.push(''):actualPOprArr.push(data.oprLoss[i].actualPOpr);
          	    	data.oprLoss[i].targetPOpr==0?targetPOprArr.push(''):targetPOprArr.push(data.oprLoss[i].targetPOpr);
          	    	data.oprLoss[i].actualEOpr==0?actualEOprArr.push(''):actualEOprArr.push(data.oprLoss[i].actualEOpr);
          	    	data.oprLoss[i].targetEOpr==0?targetEOprArr.push(''):targetEOprArr.push(data.oprLoss[i].targetEOpr);
          	    }
      	    }
        	
        	var	option = {
					 grid:{
						    x:50,
			                y:30,
			                x2:50,
			                y2:30,
			                x3:50,
			                y3:30,
			                x4:50,
			                y4:30,
			                borderWidth:1,
			            },
						title: {  
	                        //主标题文本，'\n'指定换行  
	                        text: 'Trend Chart OPR (12 Week)',  
	                        //水平安放位置，默认为左侧，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）  
	                        x: 'left',  
	                        //垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）  
	                       y: 'top'  ,
	                       textStyle:{ fontSize:13 },
	                    }, 

				    color: ['#2E9AFE', '#FFA500','#ed4d00','#114377'],
				    //calculable: true,
				    xAxis: [
				        {
				            type: 'category',
				            boundaryGap: false,
				            axisTick: {show: true},
				            axisLabel :{
				                interval:0,
				                //rotate:45,   //倾斜度
				                fontSize:10
				            },
				            data: date
				        }
				    ],
				  
				    yAxis: [{
					    	type:'value',
					    	scale:true,
					    	max:100,
					    	min:0,
					    	splitNumber:5
				    }],
				    legend: {
				        data: ['Actual E-OPR','Target E-OPR','Actual P-OPR','Target P-OPR'],
				        //orient: 'vertical',
				    	x:'right',
				    	y:'top',
				    
				    },
				    series: [
				        {
				            name: 'Actual E-OPR',
				            type: 'line',
				            barGap: 0,
				            label: labelOption,
				            data: actualEOprArr,
				            hoverAnimation:false,//禁用鼠标悬停弹出效果
				        },
				        {
				            name: 'Target E-OPR',
				            type: 'line',
				            //label: labelOption,
				            data: targetEOprArr,
				            //hoverAnimation:false,
				        },
				        {
				            name: 'Actual P-OPR',
				            type: 'line',
				            barGap: 0,
				            label: labelOption,
				            data: actualPOprArr,
				            hoverAnimation:false,//禁用鼠标悬停弹出效果
				        },
				        {
				            name: 'Target P-OPR',
				            type: 'line',
				            //label: labelOption,
				            data: targetPOprArr,
				            //hoverAnimation:false,
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
        url: baseURL + 'report/loss/minutesLoss',
        data: params,
        dataType: "json",
        success: function(data){
        	var date = [];
      	    var lossArr = [];
      	    if(data.minutesLoss!=null&&data.minutesLoss.length>0){
      	    	
      	    	 for(var i=0;i<data.minutesLoss.length;i++){
           	    	date.push(data.minutesLoss[i].weekNo2);
           	    	data.minutesLoss[i].loss==0?lossArr.push(''):lossArr.push(data.minutesLoss[i].loss);
           	    }
      	    }
      	   
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
	                        text: 'Trend Chart Minutes Loss (12 Week)',  
	                        //水平安放位置，默认为左侧，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）  
	                        x: 'left',  
	                        //垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）  
	                       y: 'top'  ,
	                       textStyle:{fontSize:13},
	                    }, 
	                    
				    color: ['#2E9AFE'],
				    //calculable: true,
				    xAxis: [
				        {
				            type: 'category',
				            boundaryGap: false,
				            axisTick: {show: true},
				            axisLabel :{
				                interval:0,
				                //rotate:45,
				                fontSize:10
				            },
				            data: date
				        }
				    ],
				    yAxis: [
				    	{
				    	type:'value'
				    	}
				    ],
				    legend: {
				        data: ['Loss'],
				    	x:'right',
				    	y:'top',
				    
				    },
				    series: [
				        {
				            name: 'Loss',
				            type: 'line',
				            //barGap: 0,
				            label: labelOption,
				            data: lossArr,
				            hoverAnimation:false,
				        },
				    ],
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
        url: baseURL + 'report/loss/equipmentLoss',
        data: params,
        dataType: "json",
        success: function(data){
        
      	    var facilityArr = [];
      	    var durationArr = [];
      	    if(data.equLoss!=null&&data.equLoss.length>0){
      	    	
      	    	for(var i=0;i<data.equLoss.length;i++){
          	    	data.equLoss[i].facilityId2==0?facilityArr.push(''):facilityArr.push(data.equLoss[i].facilityId2);
          	    	data.equLoss[i].duration==0?durationArr.push(''):durationArr.push(data.equLoss[i].duration);
          	    }
      	    }
      	    
        	var	option = {
					 grid:{
						    x:30,
			                y:10,
			                x2:50,
			                y2:30,
			                borderWidth:1,
			            },
						title: {  
	                        //主标题文本，'\n'指定换行  
	                        text: 'Production Loss Pareto',  
	                        //水平安放位置，默认为左侧，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）  
	                        x: 'left',  
	                        //垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）  
	                       y: 'top'  ,
	                       textStyle:{fontSize:13},
	                    }, 
				    color: ['#2E9AFE'],
				    xAxis: [
				        {
				        	type: 'category',
				        	// boundaryGap: false,
				            axisTick: {show: true},
				            axisLabel :{
				                interval:0,
				                //rotate:45,
				                fontSize:10
				            },
				            data: facilityArr
				        }
				    ],
				    yAxis: [{type:'value'}],
				    legend: {
				        data: ['Duration'],
				        //orient: 'vertical',
				    	x:'right',
				    	y:'top',
				    
				    },
				    series: [
				        {
				            name: 'Duration',
				            type: 'bar',
				            label: labelOption,
				            data: durationArr,
				            hoverAnimation:false,
				        },
				    ]
				};
        	 //每次窗口大小改变的时候都会触发onresize事件，这个时候我们将echarts对象的尺寸赋值给窗口的大小这个属性，从而实现图表对象与窗口对象的尺寸一致的情况
	        window.onresize = echart2.resize;
	        echart2.setOption(option);
        }
	});
}

function report(param) {
	var area = $("#area_search").val();
	var zone = $("#zone_search").val();
	var shift = $("#shift_search").val();
	var jobId = $("#jobId_search").val();
	var formWeek = $("#startTime").val();
	var toWeek = $("#endTime").val();
	var fromDate = $('#fDate').val();
	var toDate = $('#tDate').val();
	var f_date = fromDate==null?'':JSON.stringify(fromDate);
	var t_date = toDate==null?'':JSON.stringify(toDate);
	fDates = f_date.replace(new RegExp('"',"gm"),'');
	tDates = t_date.replace(new RegExp('"',"gm"),'');
	
	
	var params = {
			shop : $("#shop_search").val(),
			area : isNullOrBlank(area)?'ALL':area,
			zone : isNullOrBlank(zone)?'ALL':zone,
			shift : isNullOrBlank(shift)?'ALL':shift,
			formWeek : formWeek,
			toWeek : toWeek,
			jobId : isNullOrBlank(jobId)?'ALL':jobId,
			fromDates : fDates,
			toDates : tDates,
	} ;
	//图片
	var image = new Image();
	var image1 = new Image();
	var image2 = new Image();
	
	image.src = echart.getDataURL({
		type:"png",
        pixelRatio: 2,
        backgroundColor: '#fff',
        width:'890px'
	});
	image1.src = echart1.getDataURL({
		type:"png",
        pixelRatio: 2,
        backgroundColor: '#fff',
        width:'890px'
	});
	image2.src = echart2.getDataURL({
		type:"png",
        pixelRatio: 2,
        backgroundColor: '#fff'
	});
	
	var echarepxport = image.src.replace("data:image/png;base64,", "");
	var echarepxport1 = image1.src.replace("data:image/png;base64,", "");
	var echarepxport2 = image2.src.replace("data:image/png;base64,", "");
	
	params.echarepxport = echarepxport;
	params.echarepxport1 = echarepxport1;
	params.echarepxport2 = echarepxport2;

	//table preDownTime
	var tableDownTimetrleg = $('#preDownTime tbody').find('tr').length;
	var tableDownTimetdleg = $('#preDownTime tbody').find('tr').eq(0).find('td').length;
	var tableDownTimeArray = {};
	var x = 0;
	for(var i = 0 ;i<tableDownTimetrleg;i++){
		for(var j = 0 ; j<tableDownTimetdleg ; j++){
			if( j != 6 && j != 7){
				params['dTime_'+x] = $.trim($('#preDownTime tbody tr:eq(' + i + ') td:eq(' + j + ')').text());
				x++;
			}else if(j == 6){
				params['dTime_'+x] = tableDownTimeparetoImgArray[i].replace("data:image/png;base64,", "");
				x++;
			}else if(j == 7){
				params['dTime_'+x] = tableDownTimestatusImgArray[i].replace("data:image/png;base64,", "");
				x++;
			}
		}
	}
	
	//table preOcc
	var tableOccparamstrleg = $('#preOcc tbody').find('tr').length;
	var tableOccparamstdleg = $('#preOcc tbody').find('tr').eq(0).find('td').length;
	var tableOccparamsArray = {};
	var y = 0;
	for(var a = 0 ; a<tableOccparamstrleg ; a ++){
		for(var b = 0 ; b < tableOccparamstdleg ; b ++){
			if(b != 6 && b != 7){
				params['occ_'+y] = $.trim($('#preOcc tbody tr:eq(' + a + ') td:eq(' + b + ')').text());
				y++;
			}else if(b == 6){
				params['occ_'+y] = tableOccparetoImgArray[a].replace("data:image/png;base64,", "");
				y++;
			}else if(b == 7){
				params['occ_'+y] = tableOccstatusImgArray[a].replace("data:image/png;base64,", "");
				y++;
			}
		}
	}
	
	params.tableOccparamsArray = tableOccparamsArray;
	params.t10DTimeOld = $("#top10DownTimeOld").text();
	params.t10DTimeNew = $("#top10DownTimeNew").text();
	params.dTimeOldTol = $("#totalDownTimeOld").text();
	params.dTimeNewTol = $("#totalDownTimeNew").text();
	
	params.t10OccOld = $("#top10OccurrenceOld").text();
	params.t10OccNew = $("#top10OccurrenceNew").text();
	params.occOldTol = $("#totalOccurrenceOld").text();
	params.occNewTol = $("#totalOccurrenceNew").text();
	createReportInput(params);
	if('doc'== param){
		document.getElementById("fromexport").action = baseURL +'modules/report/panel/report';
	}else if('xls' == param) {
		document.getElementById("fromexport").action = baseURL +'modules/report/panel/report/excel';
	}
	$("#fromexport").submit();
}

function createReportInput(params,tableDownTimeArray,tableOccparamsArray){
	var inputParams = '';
	var inputSearch = '';
	for(var index in params){
		inputSearch += '<input type = "hidden" name = "'+index+'" value = "'+params[index]+'"/>'
	}
	inputParams += inputSearch;
	//console.log("输入参数："+inputParams)
	$("#fromexport").prepend(inputParams);
}
