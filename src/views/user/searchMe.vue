<template>
    <div class="main">
        <div>
            <el-button type="success" icon="el-icon-edit" circle @click="AddPost"></el-button>
            <el-button type="warning" plain icon="el-icon-user" @click="want">申请管理员</el-button>
        </div>
        <el-card class="box-card" v-for="(item, index) in listdata" :key="index">
            <div slot="header" class="clearfix" style="display: flex; justify-content: space-between; align-items: center;">
                <span>发布者：{{ item.username }}</span>
                <div>
                    <el-button size="mini" type="danger" @click="deletep(item.id)">删除</el-button>
                    <el-button size="mini" type="primary" @click="updatep(item)">修改</el-button>
                </div>
            </div>


            <div>
                <div>内容：{{ item.describe }}</div>
            </div>
            <div>
                发布时间：{{ item.date }}
            </div>
        </el-card>
        <el-dialog :title="isAdd ? '增加' : '修改'" :visible.sync="isOccur" width="30%" :before-close="handleClose">
            <el-form ref="form" :model="form" label-width="80px">

                <el-form-item label="发帖内容">
                    <el-input type="textarea" v-model="form.describe"></el-input>
                </el-form-item>
            </el-form>

            <span slot="footer" class="dialog-footer">
                <el-button @click="isOccur = false">取 消</el-button>
                <el-button type="primary" @click="confirm()">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
import { selfPullf, deltePullf, updatePullf, wantAdminf, pullLosef } from '../../utils/api/userApi'
export default {
    data() {
        return {
            listdata: [],
            isOccur: false,
            isAdd: true,//判断是发表帖子还是修改帖子
            form: {
                id: '',
                username: localStorage.getItem("username"),
                tel: localStorage.getItem("tel"),
                describe: '',
                Picaddr: '',
                type: localStorage.getItem("Infoid"),
                date: ''
            }
        }
    },
    mounted() {
        selfPullf({ id: localStorage.getItem("id") }).then(res => {
            this.listdata = res.data.data
        })
    },
    computed: {
    },
    methods: {
        deletep(id) {

            this.$confirm('此操作将永久删除该帖子, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {

                deltePullf({ id: id });
                this.$message({
                    type: 'success',
                    message: '删除成功!'
                });
                this.$router.go(0);
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });

        },
        //点击修改按钮
        updatep(item) {
            this.isAdd = false
            this.isOccur = true;
            this.form = item;
        },
        //点击发帖按钮
        AddPost() {
            this.isAdd = true;
            this.isOccur = true;
        },
        handleClose(done) {
            this.$confirm('确认关闭？')
                .then(_ => {
                    done();
                })
                .catch(_ => { });
        }
        ,
        //提交表单
        confirm() {
            this.isOccur = false;
            if (this.isAdd) {//发帖表单
                pullLosef(this.form).then(res => {
                    if (res.data.message === 'success') {
                        this.$notify({
                            title: '成功',
                            message: '操作成功',
                            duration: 0,
                            type: 'success',
                            type: 'warning'
                        });
                        this.$router.go(0);
                    } else if (res.data.message === 'lose auth') {
                        this.$notify({
                            title: '警告',
                            message: '您正在处于禁言中，请稍后再试',
                            duration: 0
                        });
                    }
                    else {
                        this.$notify({
                            title: '错误',
                            message: '系统繁忙，请稍后再试',
                            duration: 0,
                            type: 'warning'
                        });
                    }
                })

            } else {//修改表单
                this.isOccur = false;
                updatePullf(this.form).then(res => {
                    if (res.data.message === 'success') {
                        this.$notify({
                            title: '成功',
                            message: '操作成功',
                            duration: 0,
                            type: 'success'
                        });
                        this.form = {
                            id: '',
                            username: localStorage.getItem("username"),
                            tel: localStorage.getItem("tel"),
                            describe: '',
                            Picaddr: '',
                            type: localStorage.getItem("Infoid"),
                            date: ''
                        }
                    }
                    else {
                        this.$notify({
                            title: '错误',
                            message: '操作失败，请稍后再试',
                            duration: 0,
                            type: 'warning'
                        });
                    }
                })
            }
        },
        //申请管理员
        want() {
            wantAdminf({ id: localStorage.getItem("id") }).then(res => {
                if (res.data.message === 'success') {
                    this.$notify({
                        title: '成功',
                        message: '申请已发送',
                        duration: 0,
                        type: 'success'
                    });
                } else {
                    this.$notify({
                        title: '错误',
                        message: '申请失败，请稍后再试',
                        duration: 0,
                        type: 'warning'
                    });
                }
            })
        }
    }
}
</script>
<style scoped>
.box-card {
    margin-top: 5px;
}
</style>
