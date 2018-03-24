<#include "../layout.ftl"/><@adminLayout>
<h2 class="page-header">账户管理</h2>
<form class="form-inline search-form" id="search_condition">
    <div class="form-group">
        <input type="text" class="form-control" id="account" placeholder="账户" v-model="account">
    </div>
    <div class="form-group">
        <input type="text" class="form-control" id="name" placeholder="姓名" v-model="name" value="">
    </div>
    <div class="form-group">
        <input type="text" class="form-control" id="phone" placeholder="手机号" v-model="phone" value="">
    </div>
    <button type="button" class="btn btn-default" v-on:click="search">查询</button>
    <button type="button" class="btn btn-default" v-on:click="add">新增</button>
</form>
<div id="data-table">
    <vue-table v-bind:page="page"></vue-table>
</div>
<div class="modal fade" tabindex="-1" role="dialog" id="del_confirm">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">确认删除</h4>
            </div>
            <div class="modal-body">
                确认删除账号{{account}}吗
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" v-on:click="submit">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div>
</div>
<script src="${request.contextPath}/assets/js/account/list.js?2018032401"></script>
<script src="${request.contextPath}/assets/js/common/datetimepicker.js?2018032401"></script>
</@adminLayout>