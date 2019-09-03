$(function () {
	//初始化工厂及下级
	initShopSelected();
	//初始化时间频率
	frequencySelected();
	//初始化班次
	initpanelTableTitle();
	shiftSelected();
	initDate();
	$('#preDownTimeFoot').hide();
	$('#preOccFoot').hide();
	selectChange();
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

var targetTa = '';

function initpanelTableTitle(params){

	$('#panelTableHeader').empty();   //每次变化时清空所有子节点
	var table = '';
	var tabletdf = '<tbody>';
	var tablebody = '<tr>'
			+ '<td>车间</td>'
			+ '<td></td>'
			+ '<td>区域</td>'
			+ '<td></td>'
			+ '</tr>'
			
			+ '<tr>'
			+ '<td>Zone</td>'
			+ '<td></td>'
			+ '<td>班次</td>'
			+ '<td></td>'
			+ '</tr>'
			
			+ '<tr>'
			+ '<td>From Week</td>'
			+ '<td></td>'
			+ '<td>From Dates</td>'
			+ '<td></td>'
			+ '</tr>'
			
			+ '<tr>'
			+ '<td>To Week</td>'
			+ '<td></td>'
			+ '<td>To Dates</td>'
			+ '<td></td>'
			+ '</tr>'
			
			+ '<tr>'
			+ '<td>车型</td>'
			+ '<td></td>'
			+ '<td>报表生成时间</td>'
			+ '<td></td>'
			+ '</tr>'
			
			+ '<tr>'
			+ '<td>Target TA</td>'
			+ '<td></td>'
			+ '<td></td>'
			+ '<td></td>'
			+ '</tr>'
	var tabletde = '</tbody>';
	table += (tabletdf + tablebody + tabletde);
	$('#panelTableHeader').html(table);
//	console.log($('#equFaultTableHeader tr:eq(0)').children('td').length);   获取首个tr下td 的个数
	var lengths = $('#panelTableHeader tr:eq(0)').children('td').length;
	for(var i = 1 ; i <=lengths;i++){
		if(i%2 != 0){
			$('#panelTableHeader td:nth-child('+i+')').css({"font-weight":"bold","width":"10%","background":"#c0c0c0"});
		}else{
			$('#panelTableHeader td:nth-child('+i+')').html("All");
		}
	}
	$('#panelTableHeader td:nth-child(2)').css({"width":"20%"});
	$('#panelTableHeader td:nth-child(4)').css({"width":"60%"});
	$('#panelTableHeader tr:last-child td:last-child').html('');
	$('#panelTableHeader tr:eq(4) td:eq(3)').html('');
	
	if(params != null && params != ''){
		
		if(params.shop != null && params.shop.trim() != ''){
			$('#panelTableHeader tr:eq(0) td:eq(1)').html(params.shop);
		}else{
			$('#panelTableHeader tr:eq(0) td:eq(1)').html("All");
		}
		
		if(params.area != null && params.area.trim() != ''){
			$('#panelTableHeader tr:eq(0) td:eq(3)').html(params.area);
		}else{
			$('#panelTableHeader tr:eq(0) td:eq(3)').html("All");
		}
		
		if(params.zone != null && params.zone.trim() != ''){
			$('#panelTableHeader tr:eq(1) td:eq(1)').html(params.zone);
		}else{
			$('#panelTableHeader tr:eq(1) td:eq(1)').html("All");
		}
		
		if(params.shift != null && params.shift.trim() != ''){
			$('#panelTableHeader tr:eq(1) td:eq(3)').html(params.shift);
		}else{
			$('#panelTableHeader tr:eq(1) td:eq(3)').html("All");
		}
		
		if(params.sTime != null && params.sTime.trim() != ''){
			$('#panelTableHeader tr:eq(2) td:eq(1)').html(params.sTime);
		}else{
			$('#panelTableHeader tr:eq(2) td:eq(1)').html("All");
		}
		
		if(params.fromDates != null){
			var fromDate = '';
			for(var index in params.fromDates){
				fromDate += params.fromDates[index]+", ";
			}
			$('#panelTableHeader tr:eq(2) td:eq(3)').html(fromDate);
		}else{
			$('#panelTableHeader tr:eq(2) td:eq(3)').html("");
		}
		
		if(params.eTime != null && params.eTime.trim() != ''){
			$('#panelTableHeader tr:eq(3) td:eq(1)').html(params.eTime);
		}else{
			$('#panelTableHeader tr:eq(3) td:eq(1)').html("All");
		}
		if(params.toDates != null){
			var toDate = '';
			for(var index in params.toDates){
				toDate += params.toDates[index]+", ";
			}
			$('#panelTableHeader tr:eq(3) td:eq(3)').html(toDate);
		}else{
			$('#panelTableHeader tr:eq(3) td:eq(3)').html("");
		}
		
		if(params.jobId != null && params.jobId.trim() != ''){
			$('#panelTableHeader tr:eq(4) td:eq(1)').html(params.jobId);
		}else{
			$('#panelTableHeader tr:eq(4) td:eq(1)').html("All");
		}
		
		
		$('#panelTableHeader tr:eq(5) td:eq(1)').html(targetTa);
		
		var mydate = new Date();
		var createTime = mydate.getFullYear() + '-'+ Appendzero(mydate.getMonth()+1) + '-' + Appendzero(mydate.getDate()) +'  '+mydate.getHours() + ':' + Appendzero(mydate.getMinutes())+':'+Appendzero(mydate.getSeconds());
		$('#panelTableHeader tr:eq(4) td:eq(3)').html(createTime);
	}

}

function Appendzero(obj){
	if (obj < 10) {
		return "0" + "" + obj;
	} else {
		return obj;
	}
}

function initPreDownTime(queryParams){ 
	var fdates = queryParams.fDate; //显示在title内的开始时间段
	var tdates = queryParams.tDate; //显示在title内的结束时间段
	var occ = ''; //用于显示Perato中的次数
	var mins = ''; //用于显示Perato中的时间
	var totalDuration1 = ''; //总持续时间，来源于数据库查询For开始时间段
	var totalDuration2 = ''; //总持续时间，来源于数据库查询For结束时间段
	var operateFormatter = function (value, row, index) {//动态修改故障变化灯
		if(value != undefined){
			var color = 'style = "background:#ffffff;border:1px solid #666;border-radius:50%;box-shadow:0 0 1px 1px;padding:7px;outline:none;"';
			switch(value){
				case 0 : color = 'style="background:red;border:1px solid #666;border-radius:50%;box-shadow:0 0 1px 1px; padding:7px;outline:none;"';
				 break;
				case 1 : color = 'style="background:black;border:1px solid #666;border-radius:50%;box-shadow:0 0 1px 1px; padding:7px;outline:none;"';
				 break;
				case 2 : color = 'style="background:yellow;border:1px solid #666;border-radius:50%;box-shadow:0 0 1px 1px;padding:7px;outline:none;"';
				 break;
				case 3 : color = 'style="background:green;border:1px solid #666;border-radius:50%;box-shadow:0 0 1px 1px;padding:7px;outline:none;"';
				 break;
			}
			return [ '<div class = "statusDownTime_'+index+'"><button type="button" class="btn btn-circle btn-lg" '+color+'></button></div>' ].join('');
		}
	     return '';
	}
	var  responseHandler = function(res) { // 格式化数据--赋值汇总
        if (res.preDownTimeList !=null && res.preDownTimeList.length > 0){
            tmp = {
                total : res.totalCount,
                rows : res.preDownTimeList
            };
            
            var list = res.preDownTimeList;
            for(var i=0;i<list.length;i++ ){
         	   if(i==0
         			   &&list[i].totalDuration1!=null
         			   &&list[i].totalDuration2!=null){
         		   totalDuration1 = list[i].totalDuration1;
         		   totalDuration2 = list[i].totalDuration2;
         		   $('#totalDownTimeOld').html(totalDuration1);
         		   $('#totalDownTimeNew').html(totalDuration2);
         	   }
            }
       
         return tmp;
        }
        if(res.totalCount==null){
        	 tmp = {
                     total : '',
                     rows : ''
                 };
	        $('#totalDownTimeOld').html('0.00');
	   		$('#totalDownTimeNew').html('0.00');
	   		setPorpByClass('select','form-control','disabled',false);
			setPorpByClass('input','form-control','disabled',false);
			setPorpByName('select','select','disabled',false);
        	return tmp;
        }
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
	
	//获取故障时间
	var getMinsOld = function(value,row,index){
		if(value!=null){
		  mins1 = value;
		  var count1 = 0;
	      for (var i in value) {
	          count1 += value[i].mins1;
	      }
	      //开始时间表前十故障时间显示在页脚
	      $('#top10DownTimeOld').html(count1.toFixed(2));
		}
		return mins1;
	}
	
	//获取累加故障持续时间
	var getMinsNew = function(value,row,index){
		if(value!=null){
			mins2 = value;
			 var count2 = 0;
		      for (var i in value) {
		          count2 += value[i].mins2;
		      }
		}
		//结束时间表前十故障时间显示在页脚
	    $('#top10DownTimeNew').html(count2.toFixed(2));
		return mins2;
	}
	
	//根据次数和持续时间动态展示Pareto
	var paretoFormatter = function(value,row,index){
		if(mins!=null){
			var width1 = occ>100?100:occ;
			var width2 = mins>100?100:mins;
			var dom =  '<div class = "paretoDownTime_'+index+'"><input type="text" disabled="disabled" style="background-color: yellow;border: none;height: 12px;width: '+
			width1 +'px"><span style="font-size:12px">'+ occ +'</span></input></br><input type="text" disabled="disabled" style="background-color: green;border: none;height: 12px;width: '+ 
			width2.toFixed(1) +'%"><span style="font-size:12px">'+ mins.toFixed(2) +'</span></input></div>'
			
			return dom;
		}
	}
	
	var borderFormatter =  function(value,row,index){
		return {css:{"border-right-style":"double"}}
	}
	
	$('#preDownTime').empty();
	$('#preDownTime').bootstrapTable('destroy').bootstrapTable({
		url: 'panel/listPrePanel', 
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
	                 title : "<span>Previous Faults Ranked by Downtime</span></br><span>Days:"+fdates+"</span>",
	                 halign : "center",
	                 align : "center",
	                 colspan : 8
		        },{
	                 title : "<span>Root Cause Analysis</span></br><span>[Ranked by Downtime]</span>",
	                 halign : "center",
	                 align : "center",
	                 colspan : 4
		        },{
	                 title : "<span>Current Faults Ranked by Downtime</span></br><span>Days:"+tdates+"</span>",
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
				  formatter: fontFormatter,
				 }, {
				  field: 'mins1',
				  title: 'Mins',
				  cellStyle: getMins,
				  footerFormatter: getMinsOld,
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
				  formatter: fontFormatter,
				 }, {
				  field: 'mins2',
				  title: 'Mins',
				  footerFormatter: getMinsNew,
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
		 /*onLoadSuccess: function (data) { 		// 加载成功时执行
			 $('#preDownTimeFoot').show();
			 //预先处理表格转换图片(base64)作导出时使用
			 createDownTimeExportImg();
		 },*/
		 onPostBody : function (data){
			 $('#preDownTimeFoot').show();
			//预先处理表格转换图片(base64)作导出时使用
			 createDownTimeExportImg();
		 },
	     onLoadError: function (res) { 			//加载失败时执行
	     }
	});
}

function createDownTimeExportImg(){
	var tableDownTimetrleg = $('#preDownTime tbody').find('tr').length;
	var tableDownTimetdleg = $('#preDownTime tbody').find('tr').eq(0).find('td').length;
	tableDownTimeparetoImgArray = new Array();
	tableDownTimestatusImgArray = new Array();
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
		var w = '';
		var h = '';
		if(j == 6){
			_canvas = document.querySelector('.paretoDownTime_'+i);
		}
		if(j == 7){
			_canvas = document.querySelector('.statusDownTime_'+i);
		}
		if(_canvas!=null){
			w = parseInt(window.getComputedStyle(_canvas).width);
			h = parseInt(window.getComputedStyle(_canvas).height);
		}else{
			w = 1;
			h = 1;
		}
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
		    if(j == 6){
		    	tableDownTimeparetoImgArray[i] = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADsAAAAnCAYAAACxMTBTAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAABASURBVGhD7c8BAQAwDMOg+zfd+2DBAW+HlFWVVZVVlVWVVZVVlVWVVZVVlVWVVZVVlVWVVZVVlVWVVZVVlTVtH1iz0hpkBny1AAAAAElFTkSuQmCC';
			}
			if(j == 7){
				tableDownTimestatusImgArray[i] = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADsAAAAnCAYAAACxMTBTAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAABASURBVGhD7c8BAQAwDMOg+zfd+2DBAW+HlFWVVZVVlVWVVZVVlVWVVZVVlVWVVZVVlVWVVZVVlVWVVZVVlTVtH1iz0hpkBny1AAAAAElFTkSuQmCC';
			}
		});
		setPorpById('exportBtn','disabled',false);
		setPorpByClass('select','form-control','disabled',false);
		setPorpByClass('input','form-control','disabled',false);
		setPorpByName('select','select','disabled',false);
	},7000);
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
	tableOccparetoImgArray = new Array();
	tableOccstatusImgArray = new Array();
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
	var w = '';
	var h = '';
	if(j == 6){
		_canvas = document.querySelector('.paretoOcc_'+i);
	}
	if(j == 7){
		_canvas = document.querySelector('.statusOcc_'+i);
	}
	
	if(_canvas!=null){
		w = parseInt(window.getComputedStyle(_canvas).width);
		h = parseInt(window.getComputedStyle(_canvas).height);
	}else{
		w = 1;
		h = 1;
	}
	
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
	    if(j == 6){
			tableOccparetoImgArray[i] = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADsAAAAnCAYAAACxMTBTAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAABASURBVGhD7c8BAQAwDMOg+zfd+2DBAW+HlFWVVZVVlVWVVZVVlVWVVZVVlVWVVZVVlVWVVZVVlVWVVZVVlTVtH1iz0hpkBny1AAAAAElFTkSuQmCC';
		}
		if(j == 7){
			tableOccstatusImgArray[i] = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADsAAAAnCAYAAACxMTBTAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAABASURBVGhD7c8BAQAwDMOg+zfd+2DBAW+HlFWVVZVVlVWVVZVVlVWVVZVVlVWVVZVVlVWVVZVVlVWVVZVVlTVtH1iz0hpkBny1AAAAAElFTkSuQmCC';
		}
	});
}

function queryReport(tag,params){
	var sTime = params.sTime;
	var eTime = params.eTime;
	var fromDates = params.fromDates;
	var toDates = params.toDates;
	if(isNullOrBlank(sTime)){
		alert("请选择开始时间");
    	return ;
	}
	if(isNullOrBlank(eTime)){
		alert("请选择结束时间");
    	return ;
	}
	if(isNullOrBlank(fromDates)){
		alert("请选择对比开始周");
    	return ;
	}
	if(isNullOrBlank(toDates)){
		alert("请选择对比结束周");
    	return ;
	}
	
	
	var url = baseURL + 'report/panel/list';
	if(tag=='9Panel'){
		
		echars(params);
		echars1(params);
		echars2(params);
		
		getTa(params);
		
		initpanelTableTitle(params)
		initPreDownTime(params)
		initOccTab(params);
	}
	setPorpById('exportBtn','disabled',true);
	setPorpByClass('select','form-control','disabled',true);
	setPorpByClass('input','form-control','disabled',true);
	setPorpByClass('select','selectpicker show-tick form-control','disabled',true);
	setPorpByName('select','select','disabled',true);
	clearForm("fromexport");
}

function getTa(params){
	$.ajax({
        type: "post",
        url: baseURL + 'modules/report/panel/getTa',
        data: params,
        dataType: "json",
        async: false,
        success: function(data){
        	targetTa = data.tarTa;
        	console.log(targetTa)
        }
	});
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
        url: baseURL + 'modules/report/panel/echarts',
        data: params,
        dataType: "json",
        success: function(data){
        	var date = [];
      	    var mtbf = [];
      	    var tarMtbf = [];
      	    
      	    for(var i=0;i<data.list.length;i++){
      	    	date.push(data.list[i].monday);
      	    	data.list[i].mtbf==0?mtbf.push(''):mtbf.push(data.list[i].mtbf);
      	    	data.list[i].targetMtbf==0?tarMtbf.push(''):tarMtbf.push(data.list[i].targetMtbf);
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
	                        text: 'Mean Time Between Failure(MTBF)',  
	                        //水平安放位置，默认为左侧，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）  
	                        x: 'left',  
	                        //垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）  
	                       y: 'top'  ,
	                       textStyle:{fontSize:13}
	                    }, 

				    color: ['#2E9AFE', '#FFA500'],
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
				  
				    yAxis: [  {}],
				    legend: {
				        data: ['MTBF','Target MTBF'],
				        //orient: 'vertical',
				    	x:'right',
				    	y:'top',
				    
				    },
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
				            //label: labelOption,
				            data: tarMtbf,
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
        url: baseURL + 'modules/report/panel/echarts',
        data: params,
        dataType: "json",
        success: function(data){
        	var date = [];
      	    var tav = [];
      	    var tarTav = [];
      	    
      	    for(var i=0;i<data.list.length;i++){
      	    	date.push(data.list[i].monday);
      	    	data.list[i].tav==0?tav.push(''):tav.push(data.list[i].tav);
      	    	data.list[i].targetTav==0?tarTav.push(''):tarTav.push(data.list[i].targetTav);
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
	                        text: 'Technical Availability Analysis(TA)',  
	                        //水平安放位置，默认为左侧，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）  
	                        x: 'left',  
	                        //垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）  
	                       y: 'top'  ,
	                       textStyle:{fontSize:13}
	                    }, 
	                    
				    color: ['#2E9AFE', '#FFA500'],
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
				    	type:'value',
				    	scale:true,
				    	max:120,
				    	min:0,
				    	splitNumber:5
				    	}
				    ],
				    legend: {
				        data: ['TA','Target TA'],
				        //orient: 'vertical',
				    	x:'right',
				    	y:'top',
				    
				    },
				    series: [
				    
				        {
				            name: 'TA',
				            type: 'line',
				            barGap: 0,
				            label: labelOption,
				            data: tav,
				            hoverAnimation:false,
				        },
				        {
				            name: 'Target TA',
				            type: 'line',
				            //label: labelOption,
				            data: tarTav,
				            //hoverAnimation:false,
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
        url: baseURL + 'modules/report/panel/echarts',
        data: params,
        dataType: "json",
        success: function(data){
        	
        	var date = [];
      	    var tav = [];
      	    var tarTav = [];
  	    	for(var i=11;i<data.list.length;i++){
      	    	date.push(data.list[i].monday);
      	    	data.list[i].tav==0?tav.push(''):tav.push(data.list[i].tav);
      	    	data.list[i].targetTav==0?tarTav.push(''):tarTav.push(data.list[i].targetTav);
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
	                        text: 'Technical Availability Trend Analysis',  
	                        //水平安放位置，默认为左侧，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）  
	                        x: 'left',  
	                        //垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）  
	                       y: 'top'  ,
	                       textStyle:{fontSize:13}
	                    }, 
				    color: ['#FFA500', '#6fa8dc'],
				    //calculable: true,
				    xAxis: [
				        {
				        	type: 'category',
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
					    	type:'value',
					    	scale:true,
					    	max:120,
					    	min:0,
					    	splitNumber:5
				    	}
				    ],
				    legend: {
				        data: ['TA','Target TA'],
				        //orient: 'vertical',
				    	x:'right',
				    	y:'top',
				    
				    },
				    series: [
				        {
				            name: 'Target TA',
				            type: 'line',
				            barGap: 0,
				           // label: labelOption,
				            data: tarTav,
				            //hoverAnimation:false,
				        },
				        {
				            name: 'TA',
				            type: 'bar',
				            label: labelOption,
				            data: tav,
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
	$("#fromexport").prepend(inputParams);
}
