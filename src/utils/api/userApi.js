import  request  from './request.js'
import { userInfo, updateINfo, seeList, pullLose, wantAdmin, selfPull, updatePull,deltePull } from '../url/user'
function userInfof(data) {
    return request.get(userInfo, { params: { id: data.id } })
}
function updateINfof(data) {
    return request.post(updateINfo, data)
}
// function seeListf(data) {
//     return request.get(seeList, { params: { Infoid: data.Infoid } })
// }
function seeListf(data) {
    return request({
        url: seeList,
        method: "get", params: {
            infoid: data.infoid
        }
    })
}
function pullLosef(data) {
    return request.post(pullLose, data)
}
function wantAdminf(data) {
    return request.get(wantAdmin, {params:{id:data.id}})
}
function selfPullf(data) {
    return request.get(selfPull, { params: { id: data.id } })
}
function updatePullf(data) {
    return request.post(updatePull, data)
}
function deltePullf(data){
    return request.get(deltePull,{params:{id:data.id}})
}
export {
    userInfof, updateINfof, seeListf, pullLosef, wantAdminf, selfPullf, updatePullf,deltePullf

}