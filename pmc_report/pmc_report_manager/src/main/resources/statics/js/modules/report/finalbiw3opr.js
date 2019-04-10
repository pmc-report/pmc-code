$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'report/finalbiw3opr/list',
        datatype: "json",
        colModel: [			
			{ label: '车间', name: 'shop', index: 'shop', width: 80 }, 			
			{ label: '区域', name: 'area', index: 'area', width: 80 }, 			
			{ label: 'Zone', name: 'zone', index: 'zone', width: 80 }, 			
			{ label: '故障代码', name: 'equipment', index: 'equipment', width: 80 }, 			
			{ label: '设备描述', name: 'facilityDesc', index: 'facility_desc', width: 80 }, 			
			{ label: '站点', name: 'station', index: 'station', width: 80 }, 			
			{ label: 'JobId', name: 'jobId', index: 'job_id', width: 80 }, 			
			{ label: '开始时间', name: 'startTime', index: 'start_time', width: 80 }, 			
			{ label: '结束时间', name: 'oprLevel', index: 'opr_level', width: 80 }, 			
			{ label: '班次', name: 'shift', index: 'shift', width: 80 }, 			
			{ label: '设备Id', name: 'facilityId', index: 'facility_id', width: 80 }, 			
			{ label: '停机时间', name: 'downtime', index: 'downtime', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
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
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#reportApp',
	data:{
		showList: true,
		title: null,
		finalBiw3Opr: {
			
		}
	},
	methods: {
		queryByParams: function (event) {
			
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.finalBiw3Opr),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		clear:function(){
			
		},
		queryAll: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.finalBiw3Opr = {};
		},
		update: function (event) {
			var rowId = getSelectedRow();
			if(rowId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(rowId)
		},
		saveOrUpdate: function (event) {
			var url = vm.finalBiw3Opr.rowId == null ? "report/finalbiw3opr/save" : "report/finalbiw3opr/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.finalBiw3Opr),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var rowIds = getSelectedRows();
			if(rowIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "report/finalbiw3opr/delete",
                    contentType: "application/json",
				    data: JSON.stringify(rowIds),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(rowId){
			$.get(baseURL + "report/finalbiw3opr/info/"+rowId, function(r){
                vm.finalBiw3Opr = r.finalBiw3Opr;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});