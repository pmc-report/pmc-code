$(function () {
	//初始化工厂及下级
	initShopSelected();
	//初始化时间频率
	frequencySelected();
	//初始化班次
	initpanelTableTitle();
	shiftSelected();
	initDate();
});

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
			$('#panelTableHeader tr:eq(2) td:eq(3)').html("All");
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
			$('#panelTableHeader tr:eq(3) td:eq(3)').html("All");
		}
		
		if(params.jobId != null && params.jobId.trim() != ''){
			$('#panelTableHeader tr:eq(4) td:eq(1)').html(params.jobId);
		}else{
			$('#panelTableHeader tr:eq(4) td:eq(1)').html("All");
		}
		/*if(params.eTime != null && params.eTime.trim() != ''){
			$('#panelTableHeader tr:eq(5) td:eq(1)').html(params.eTime);
		}else{
			$('#panelTableHeader tr:eq(5) td:eq(1)').html("All");
		}
		*/
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
	var occ = '';
	var mins = '';
	var totalDuration1 = '';
	var totalDuration2 = '';
	var operateFormatter = function (value, row, index) {//赋予的参数
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
			return [ '<button type="button" class="btn btn-circle btn-lg" '+color+'></button>' ].join('');
		}
	     return '';
	}
	var  responseHandler = function(res) { // 格式化数据
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
	
	var getOcc = function(value,row,index){
		if(value!=null){
			occ = value;
		}
		return occ;
	}
	
	var getMinsOld = function(value,row,index){
		if(value!=null){
		  mins = value;
		}
		return mins;
	}
	
	var getMinsNew = function(value,row,index){
		if(value!=null){
			mins2 = value;
		}
	    $('#top10DownTimeNew').html(count2.toFixed(2));
		return mins2;
	}
	
	var paretoFormatter = function(value,row,index){
		if(mins!=null){
			var width = mins>100?100:mins;
			var dom =  '<input type="text" disabled="disabled" style="background-color: yellow;border: none;height: 12px;width: '+
			occ +'px"><span style="font-size:12px">'+ occ +'</span></input></br><input type="text" disabled="disabled" style="background-color: green;border: none;height: 12px;width: '+ 
			width.toFixed(1) +'%"><span style="font-size:12px">'+ mins.toFixed(2) +'</span></input>'
		 
			return dom;
		}
	}
	
	var getTop10DownTime = function(value,row,index){
		 var count1 = 0;
	      for (var i in value) {
	          count1 += value[i].mins1;
	      }
	      $('#top10DownTimeOld').html(count1.toFixed(2));
	}
	
	/*var totalDownTime = function(value,row,index){
		return '<span style="font-size:12px">'+totalDuration1+'</span></br><span style="font-size:12px">'+totalDuration2+'</span>';
	}*/
	
	var borderFormatter =  function(value,row,index){
		return {css:{"border-right-style":"double"}}
	}
	
	$('#preDownTime').empty();
	$('#preDownTime').bootstrapTable('destroy').bootstrapTable({
		url: 'panel/listPrePanel', 
		method: "post",                     //使用get请求到服务器获取数据
	    dataType: "json",
	    contentType: "application/x-www-form-urlencoded",
		queryParamsType: '', //参数格式,发送标准的RESTFul类型的参数请求 
		search: false, //显示搜索框
		showFooter: true,  //显示底部栏
		sidePagination: "server", //服务端处理分页
		responseHandler: responseHandler,
		columns: [
				[{
	                 title : "Previous Faults Ranked by Downtime",
	                 halign : "center",
	                 align : "center",
	                 colspan : 8
		        },{
	                 title : "Root Cause Analysis(Ranked by Downtime)",
	                 halign : "center",
	                 align : "center",
	                 colspan : 4
		        },{
	                 title : "Current Faults Ranked by Downtime",
	                 halign : "center",
	                 align : "center",
	                 colspan : 5
		        }],
				[{
				  field: 'old',
				  title: 'Old',
				  formatter: fontFormatter,
				 /* footerFormatter : function(value){
						 return '<span style="font-size:12px">Total Downtime for Top 10 Faults :</span></br><span style="font-size:12px">Total Occurrence for Top 10 Faults :</span>';
				  }*/
				 }, {
				  field: 'oldThenNew',
				  title: 'New',
				  formatter: fontFormatter,
				 // formatter: top10DownTime
				 }, {
				  field: 'occ1',
				  title: 'Occ',
				  cellStyle: getOcc,
				  formatter: fontFormatter,
				 
				 }, {
				  field: 'mins1',
				  title: 'Mins',
				  cellStyle: getMinsOld,
				  formatter: fontFormatter,
				 }, {
				  field: 'stn1',
				  title: 'Stn',
				  formatter: fontFormatter,
				  footerFormatter: getTop10DownTime
				 }, {
				  field: 'description1',
				  title: 'Description',
				  formatter: fontFormatter,
				  /*footerFormatter: function(value){
					  return '<button type="button" '+
					  			'class="btn btn-circle btn-lg" '+
					  			'style="background:green;border:1px solid #666;border-radius:50%;box-shadow:0 0 1px 1px; padding:7px;outline:none;">'+
					  		 '</button><span style="font-size:12px">&nbspConcern improved</span>';
				  }*/
				 }, {
				  field: 'pareto',
				  title: 'Pareto',
				  formatter: paretoFormatter,
				  /*footerFormatter: function(value){
					  return '<button type="button" '+
					  			'class="btn btn-circle btn-lg" '+
					  			'style="background:#ffffff;border:1px solid #666;border-radius:50%;box-shadow:0 0 1px 1px; padding:7px;outline:none;">'+
					  		 '</button><span style="font-size:12px">&nbspConcern Out of 10</span>';
				  }*/
				 }, {
				  field: 'status',
				  title: 'Status',
				  align: 'center',
		          valign: 'middle',
		          cellStyle: borderFormatter,
		          formatter: operateFormatter, //自定义方法，添加操作按钮
		         /* footerFormatter: function(value){
					  return '<button type="button" '+
					  			'class="btn btn-circle btn-lg" '+
					  			'style="background:#000000;border:1px solid #666;border-radius:50%;box-shadow:0 0 1px 1px; padding:7px;outline:none;">'+
					  		 '</button><span style="font-size:12px">&nbspConcern Resolved</span>';
				  }*/
		        
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
				  /*footerFormatter : function(value){
						 return '<span style="font-size:12px">Total Downtime for All Faults :</span></br><span style="font-size:12px">Total Occurrence for All Faults :</sapn>';
				  }*/
				 }, {
				  field: 'occ2',
				  title: 'Occ',
				  formatter: fontFormatter,
				  //footerFormatter: totalDownTime
				 }, {
				  field: 'mins2',
				  title: 'Mins',
				  formatter: getMinsNew,
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
		 onLoadSuccess: function (data) { 		//加载成功时执行
	     },
	     onLoadError: function (res) { 		//加载失败时执行
	     }
	});
}

function initOccTab(params){ 
	
	var operateFormatter = function (value, row, index) {//赋予的参数
		if(value != undefined){
			var color = 'style = "border:1px solid #666;border-radius:50%;box-shadow:0 0 1px 1px;padding:7px;outline:none;"';
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
	
	var fontSize = function(value,row,index) {
        var a = '<span style="font-size:12px">'+value+'</span>';  
        return a;  
	}
	
	var getOcc = function(value,row,index){
		occ = value;
		return occ;
	}
	
	var getMins = function(value,row,index){
		mins = value;
		return mins;
	}
	
	var paretoFormatter = function(value,row,index){
		var dom =  '<input type="text" disabled="disabled" style="background-color: yellow;border: none;height: 12px;width: '+
		occ +'px"><span style="font-size:12px">'+ occ +'</span></input></br><input type="text" disabled="disabled" style="background-color: green;border: none;height: 12px;width: '+ 
		mins.toFixed(1) +'px"><span style="font-size:12px">'+ mins.toFixed(2) +'</span></input>'
	 
		return dom;
	}
	
	var top10DownTime =  function (value) {
	      var count1 = 0;
	      var count2 = 0;
	      for (var i in value) {
	    	  value[i].mins = mins;
	    	  value[i].occ = occ;
	          count1 += value[i].mins;
	          count2 += value[i].occ;
	      }
	      return '<span style="font-size:12px">'+count1.toFixed(2)+'</span></br><span style="font-size:12px">'+count2+'</span>';
	}
	
	var totalDownTime = function(value,row,index){
		return '<span style="font-size:12px">'+totalDuration1+'</span></br><span style="font-size:12px">1101</span>';
	}
	
	var borderFormatter =  function(value,row,index){
		return {css:{"border-right-style":"double"}}
	}
	
	$('#preOcc').empty();
	$('#preOcc').bootstrapTable('destroy').bootstrapTable({
		url: 'panel/listPrePanel', 
		method: "post",  
		dataType: "json",
		striped: true,   //是否显示行间隔色
		singleSelect: false,
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
		         },{
	                 title : "Root Cause Analysis(Ranked by Occurence)",
	                 halign : "center",
	                 align : "center",
	                 colspan : 4
	                
		         },{
	                 title : "Current Faults Ranked by Occurrence",
	                 halign : "center",
	                 align : "center",
	                 colspan : 5,
		         }],
				[{
				  field: 'old',
				  title: 'Old',
				  formatter: fontSize,
				  footerFormatter : function(value){
						 return '<span style="font-size:12px">Total Downtime for Top 10 Faults :</span></br><span style="font-size:12px">Total Occurrence for Top 10 Faults :</span>';
				  }
				 }, {
				  field: '_new',
				  title: 'New',
				  formatter: fontSize,
				  footerFormatter: top10DownTime
				 }, {
				  field: 'occ',
				  title: 'Occ',
				  cellStyle: getOcc,
				  formatter: fontSize,
				 }, {
				  field: 'mins',
				  title: 'Mins',
				  cellStyle: getMins,
				  formatter: fontSize,
				  footerFormatter : function(value){
						 return '<span style="font-size:12px">Total Downtime for All Faults :</span></br><span style="font-size:12px">Total Occurrence for All Faults :</sapn>';
				  }
				 }, {
				  field: 'stn',
				  title: 'Stn',
				  footerFormatter: totalDownTime,
				  formatter: fontSize,
				 }, {
				  field: 'description',
				  title: 'Description',
				  formatter: fontSize
				 }, {
				  field: 'pareto',
				  title: 'Pareto',
				  formatter: paretoFormatter,
				  footerFormatter: function(value){
					  return '<button type="button" '+
					  			'class="btn btn-circle btn-lg" '+
					  			'style="background:red;border:1px solid #666;border-radius:50%;box-shadow:0 0 1px 1px; padding:7px;outline:none;">'+
					  		 '</button><span style="font-size:12px;width:100%">&nbspConcern Worse</span>';
				  }
				 }, {
				  field: 'status',
				  title: 'Status',
				  align: 'center',
		          valign: 'middle',
		          footerFormatter: function(value){
					  return '<button type="button" '+
					  			'class="btn btn-circle btn-lg" '+
					  			'style="background:yellow;border:1px solid #666;border-radius:50%;box-shadow:0 0 1px 1px; padding:7px;outline:none;">'+
					  		 '</button><span style="font-size:12px">&nbspConcern Worse</span>';
				  },
		          cellStyle:borderFormatter,
		          formatter: operateFormatter //自定义方法，添加操作按钮
				 }, {
				  field: 'casualDescription',
				  title: 'Casual Description',
				  footerFormatter: function(value){
					  return '<button type="button" '+
					  			'class="btn btn-circle btn-lg" '+
					  			'style="background:green;border:1px solid #666;border-radius:50%;box-shadow:0 0 1px 1px; padding:7px;outline:none;">'+
					  		 '</button><span style="font-size:12px;width:100%">&nbspConcern Worse</span>';
				  }
				 }, {
				  field: 'rootCauseAnalysis',
				  title: 'Root Cause Analysis',
				  footerFormatter: function(value){
					  return '<button type="button" '+
					  			'class="btn btn-circle btn-lg" '+
					  			'style="background:#ffffff;border:1px solid #666;border-radius:50%;box-shadow:0 0 1px 1px; padding:7px;outline:none;">'+
					  		 '</button><span style="font-size:12px">&nbspConcern Worse</span>';
				  }
				 }, {
				  field: 'who',
				  title: 'Who',
				  footerFormatter: function(value){
					  return '<button type="button" '+
					  			'class="btn btn-circle btn-lg" '+
					  			'style="background:#000000;border:1px solid #666;border-radius:50%;box-shadow:0 0 1px 1px; padding:7px;outline:none;">'+
					  		 '</button><span style="font-size:12px">&nbspConcern Worse</span>';
				  }
				 }, {
				  field: 'timing',
				  title: 'Timing',
				  cellStyle: borderFormatter
				 }, {
				  field: '_new',
				  title: 'New',
				  formatter: fontSize
				 }, {
				  field: 'occ',
				  title: 'Occ',
				  formatter: fontSize
				 }, {
				  field: 'mins',
				  title: 'Mins',
				  formatter: fontSize
				 }, {
				  field: 'stn',
				  title: 'Stn',
				  formatter: fontSize
				 }, {
				  field: 'description',
				  title: 'Description',
				  formatter: fontSize
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
		
		initpanelTableTitle(params)
		initPreDownTime(params)
		//initOccTab1(params);
		
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
				            data: tarMtbf,
				            hoverAnimation:false,
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
				    series: [
				    
				        {
				            name: 'TAV',
				            type: 'line',
				            barGap: 0,
				            label: labelOption,
				            data: tav,
				            hoverAnimation:false,
				        },
				        {
				            name: 'Target TAV',
				            type: 'line',
				            label: labelOption,
				            data: tarTav,
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
	                       textStyle:{
	                   　　　　				fontSize:13
	                       },
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
				    series: [
				        {
				            name: 'TAV',
				            type: 'line',
				            barGap: 0,
				            label: labelOption,
				            data: tav,
				            hoverAnimation:false,
				        },
				        {
				            name: 'Target TAV',
				            type: 'bar',
				            label: labelOption,
				            data: tarTav,
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

function report() {
	var image = new Image();
	var image1 = new Image();
	var image2 = new Image();
	
	image.src = echart.getDataURL({
		type:"png",
        pixelRatio: 2,
        backgroundColor: '#fff'
	});
	image1.src = echart1.getDataURL({
		type:"png",
        pixelRatio: 2,
        backgroundColor: '#fff'
	});
	image2.src = echart2.getDataURL({
		type:"png",
        pixelRatio: 2,
        backgroundColor: '#fff'
	});
	
	var echarepxport = image.src.replace("data:image/png;base64,", "");
	var echarepxport1 = image1.src.replace("data:image/png;base64,", "");
	var echarepxport2 = image2.src.replace("data:image/png;base64,", "");
	document.getElementById("echarepxport").value = echarepxport;
	document.getElementById("echarepxport1").value = echarepxport1;
	document.getElementById("echarepxport2").value = echarepxport2;
	document.getElementById("fromexport").action = baseURL +'modules/report/panel/report';
	// console.log(document.getElementById("fromexport").action);
	return true;
}
