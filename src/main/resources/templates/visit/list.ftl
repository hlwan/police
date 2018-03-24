<#include "../layout.ftl"/><@adminLayout>
<h2 class="page-header">统计信息</h2>
<form class="form-inline search-form" id="search_condition">
    <div class="form-group">
        <input type="text" class="form-control" id="account" placeholder="账户" v-model="accountId">
    </div>
    <button type="button" class="btn btn-default" v-on:click="search">查询</button>
</form>
<div id="data-table">
    <vue-table v-bind:page="page"></vue-table>
</div>
<div class="modal fade" tabindex="-1" role="dialog" id="response_detail">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">响应信息</h4>
            </div>
            <div class="modal-body">
                {{detail}}
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div><!-- /.modal-content -->
    </div>
</div>
<script src="${request.contextPath}/assets/js/visit/list.js?2018090308"></script>
<script src="${request.contextPath}/assets/js/common/datetimepicker.js?2018032401"></script>
</@adminLayout>