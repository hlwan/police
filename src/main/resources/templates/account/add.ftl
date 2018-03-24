<#include "../layout.ftl"/><@adminLayout>
<h2 class="page-header">添加账户</h2>
<div class="form-horizontal" id="form_add">
    <div class="form-group">
        <label  class="col-sm-1 control-label" for="id"></label>
        <label  class="col-sm-1 control-label" for="id">账号</label>
        <div class="col-md-3">
            <input type="text" class="form-control" id="id" placeholder="账号" v-model="id">
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-1 control-label" for="password"></label>
        <label  class="col-sm-1 control-label" for="password">密码</label>
        <div class="col-md-3">
            <input type="text" class="form-control" id="password" placeholder="密码" v-model="password">
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-1 control-label" for="name"></label>
        <label  class="col-sm-1 control-label" for="name">姓名</label>
        <div class="col-md-3">
            <input type="text" class="form-control" id="name" placeholder="姓名" v-model="name">
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-1 control-label" for="phone"></label>
        <label  class="col-sm-1 control-label" for="phone">手机</label>
        <div class="col-md-3">
            <input type="text" class="form-control" id="phone" placeholder="手机" v-model="phone">
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-1 control-label" for="phone"></label>
        <label  class="col-sm-1 control-label" for="phone"></label>
        <div class="col-md-3">
            <button type="button" class="btn btn-primary" v-on:click="submit">提交</button>
            <button type="button" class="btn btn-default" v-on:click="cancel">取消</button>
        </div>
    </div>

</div>
<script src="${request.contextPath}/assets/js/account/add.js?2018032401"></script>
</@adminLayout>