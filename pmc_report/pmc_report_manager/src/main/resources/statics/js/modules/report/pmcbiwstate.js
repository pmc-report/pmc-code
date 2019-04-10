$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'report/pmcbiwstate/list',
        datatype: "json",
        colModel: [	
        	{ label: 'Station', name: 'station', index: 'station', width: 80 }, 
        	{ label: 'Equipment', name: 'equipment', index: 'equipment', width: 80 }, 
        	{ label: 'FacilityId', name: 'facilityId', index: 'facility_id', width: 80 }, 
			{ label: 'Starved', name: 'starved', index: 'starved', width: 50, key: true },
			{ label: 'Blocked', name: 'blocked', index: 'blocked', width: 80 }, 			
			{ label: 'Waiting Auxiliry Material', name: 'Waiting_AM', index: 'Waiting AM', width: 80 }, 			
			{ label: 'Bypass', name: 'bypass', index: 'bypass', width: 80 }, 			
			{ label: 'Repair in Progress', name: 'repair', index: 'repair', width: 80 }, 			
			{ label: 'Tool Change', name: 'tool_change', index: 'tool_change', width: 80 }, 			
			{ label: 'Shut Down', name: 'shut_down', index: 'shut_down', width: 80 }, 			
			{ label: 'Emergency stop', name: 'emergency_stop', index: 'emergency_stop', width: 80 }, 			
			{ label: 'Setup', name: 'setup', index: 'setup', width: 80 }, 			
			{ label: 'Break', name: 'break', index: 'break', width: 80 }, 			
			{ label: 'Waiting Attention', name: 'waiting_attention', index: 'waiting_attention', width: 80 }, 			
			{ label: 'No Communication', name: 'no_communication', index: 'no_communication', width: 80 },
			{ label: '#', name: '#_', index: '#_', width: 80 }
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
		pmcBiwState: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.pmcBiwState = {};
		},
		update: function (event) {
			var startTime = getSelectedRow();
			if(startTime == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(startTime)
		},
		saveOrUpdate: function (event) {
			var url = vm.pmcBiwState.startTime == null ? "report/pmcbiwstate/save" : "report/pmcbiwstate/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.pmcBiwState),
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
			var startTimes = getSelectedRows();
			if(startTimes == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "report/pmcbiwstate/delete",
                    contentType: "application/json",
				    data: JSON.stringify(startTimes),
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
		getInfo: function(startTime){
			$.get(baseURL + "report/pmcbiwstate/info/"+startTime, function(r){
                vm.pmcBiwState = r.pmcBiwState;
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