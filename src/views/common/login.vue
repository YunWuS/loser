<template>
    <div class="main">
        <div class="title-container">
            <h1 class="title">失物招领系统</h1>
        </div>
        <div class="center-container">

            <el-form :model="ruleForm" status-icon label-width="100px" class="form">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model.number="ruleForm.username"></el-input>
                </el-form-item>
                <el-form-item label="联系方式" prop="tel" v-if="register">
                    <el-input type="password" v-model="ruleForm.tel" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="身份选择" v-if="!register">
                    <el-radio-group v-model="ruleForm.infoid">
                        <el-radio label="拾取者"></el-radio>
                        <el-radio label="丢失者"></el-radio>
                        <el-radio label="管理员"></el-radio>
                        <el-radio label="超级管理员"></el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="密码" prop="pass">
                    <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="checkPass" v-if="register">
                    <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm')" style="margin-left: 80px;"
                        v-if="!register">登录</el-button>
                    <el-button @click="ReG" style="margin-left: 40px;" v-if="!register">注册</el-button>
                    <el-button style="margin-left: 40px;" v-if="register" @click="back">取消</el-button>
                    <el-button style="margin-left: 40px;" v-if="register" @click="confirmRe" type="primary">确认</el-button>

                </el-form-item>
            </el-form>
        </div>
    </div>
</template>
<script>
import { loginf, registerf } from '../../utils/api/comapi'

import { mess, notice } from '@/utils/UseElemnt/notice'
export default {
    data() {
        return {
            register: false,
            ruleForm: {
                password: '',
                checkPass: '',
                username: '',
                infoid: '',
                tel: ''
            },
            user: {//用于注册时使用
                id: '',
                Infoid: '',
                username: '',
                password: '',
                tel: '',
                crscore: '',
                auth: '',
                calmday: '',
            }
        };
    },
    methods: {
        ReG() {
            this.register = !this.register
        },
        submitForm(formName) {
            let name = this.ruleForm.username
            let pwd = this.ruleForm.password
            let info;
            if (this.ruleForm.infoid === '拾取者') {
                info = 1;
            }
            if (this.ruleForm.infoid === '丢失者') {
                info = 2
            } if (this.ruleForm.infoid === '管理员') {
                info = 3
            } if (this.ruleForm.infoid === '超级管理员') {
                info = 4
            }
            loginf({ username: name, password: pwd, infoid: info }).then(res => {
                if (res.data.message === 'success') {
                    let mydata = res.data.data;
                    console.log(mydata)
                    let data = mydata[0];
                    let token = mydata[1];
                    console.log(data)
                    localStorage.setItem("id", data.id);
                    localStorage.setItem("infoid", info);
                    localStorage.setItem("username", data.username)
                    localStorage.setItem("tel", data.tel)
                    localStorage.setItem("satoken", token.tokenValue)
                    this.$notify({
                        title: '提示',
                        message: '登录成功',
                        duration: 1600
                    });
                    setTimeout(() => {
                        // const currentRoute = this.$router.currentRoute;
                        // if (currentRoute.path !== '/UserHome') {
                        this.$router.push('/UserHome');
                        // }
                    }, 3000); // 2000毫秒（2秒）延迟
                }
                else {
                    this.$notify({
                        title: '提示',
                        message: '用户名或密码错误',
                        duration: 3600
                    });
                }
            })

        },

        confirmRe() {
            if (this.ruleForm.username != '' || this.ruleForm.tel != '' || this.ruleForm.password != '') {
                if (this.ruleForm.password != this.ruleForm.checkPass) {
                    notice('error', '俩次输入密码不一致，请重新输入')
                } else {
                    this.user.username = this.ruleForm.username
                    this.user.tel = this.ruleForm.tel
                    this.user.password = this.ruleForm.password
                    registerf(this.user).then(res => {
                        if (res.data.message === 'success') {
                            this.register = false;
                            mess('注册成功，请登录')
                        }
                    })

                }
            } else {
                notice('error', '有信息未填请在检查一次')
            }

        },
        //返回登录主界面
        back() {
            this.register = false;
        }
    }
}
</script>
<style scoped>
.main {
    width: 100%;
    height: 100vh;
    background-image: url('@/assets/background.jpg');
    background-size: cover;
    display: flex;
    flex-direction: column;
    /* 垂直排列子元素 */
    /* justify-content: center; */
    align-items: center;
}

.center-container {
    margin-top: 2%;
    width: 30%;
    background-color: rgb(254, 254, 254);
    border-radius: 5px;
    box-shadow: 2px 2px 5px #888888;
}

.title-container {
    margin-bottom: 20px;
    margin-top: 7%;

    /* 设置标题容器底边距，用于分隔标题和表单 */
}

.title {
    text-align: center;
    /* 让标题文本居中 */
    font-size: 50px;
    font-weight: bold;
    letter-spacing: 5px;
    color: white;
    /* 可以根据需要调整字体大小 */
}

.form {
    /* 设置表单样式 */
    margin-top: 13px;
    width: 85%;
    height: 80%;
}
</style>
