<template>
    <div class="main">
        <el-card class="box-card" v-for="(item, index) in listdata" :key="index">
            <div slot="header" class="clearfix" style="display: flex; justify-content: space-between; align-items: center;">
                <span>
                    <i class="el-icon-user"></i>
                    发布者：{{ item.username }}</span>
                <!-- <el-button size="mini" type="primary" v-if="infoid == 3">修改</el-button> -->
                <div>
                    <el-button size="mini" type="warning" icon="el-icon-delete" v-if="infoid == 3"
                        @click="deletepo(item.id)">删除</el-button>
                    <el-button size="mini" type="danger" icon="el-icon-document-delete" v-if="infoid == 3"
                        @click="remove(item.username)">扣除信誉分</el-button>
                </div>
            </div>
            <div class="content">
                <div>
                    <i class="el-icon-tickets"></i> {{ item.describe }}
                </div>
            </div>
            <el-divider></el-divider>
            <div class="common">
                <i class="el-icon-phone-outline"></i> 联系方式：{{ item.tel }}
            </div>
            <!-- <el-divider></el-divider> -->
            <div class="common">
                <i class="el-icon-date"></i> 发布时间：{{ item.date }}
            </div>
        </el-card>
        <el-dialog title="扣除信誉积分" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
            <el-input v-model="score" placeholder="请输入扣除的分数"></el-input>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="confirm">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
import { seeListf, deltePullf } from '../../utils/api/userApi'
import { minScoref } from '../../utils/api/adminApi'
import { notice, mess } from '../../utils/UseElemnt/notice'
export default {
    data() {
        return {
            listdata: [],
            infoid: '',
            dialogVisible: false,
            score: '',
            username: ''
        }
    },
    mounted() {
        this.infoid = localStorage.getItem("infoid");

        //根据infoid不同返回不同数据，infoid :1 拾取者，2丢失者 ，3 管理员：返回全部数据
        seeListf({ infoid: localStorage.getItem("infoid") }).then(res => {
            this.listdata = res.data.data;
        })

    },
    computed: {
    },
    methods: {
        //删除帖子
        deletepo(tid) {
            this.$confirm('此操作将永久删除记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                deltePullf({ id: tid })
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
        //打开扣除分数弹窗
        remove(username) {
            this.dialogVisible = true;
            this.username = username;
        },
        //确定提交扣除分数
        confirm() {
            this.dialogVisible = false;
            minScoref({ username: this.username, score: this.score }).then(res => {
                if (res.data.message === 'success') {
                    // notice('success', '操作成功')
                    mess('操作成功')
                } else {
                    notice('error', '操作失败请稍后再试')
                }
            })
        },
        //关闭弹窗
        handleClose(done) {
            this.$confirm('确认关闭？')
                .then(_ => {
                    done();
                })
                .catch(_ => { });
        },

    }
}
</script>
<style scoped>
.box-card {
    margin-top: 5px;
}

.content {
    font-weight: bolder;
    font-size: 20px;
}
</style>
../../utils/UseElemnt/notice