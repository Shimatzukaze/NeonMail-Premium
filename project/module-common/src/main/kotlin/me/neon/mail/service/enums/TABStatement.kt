package me.neon.mail.service.enums

import me.neon.mail.service.SQLImpl


/**
 *
 * **已确认使用的语句会以枚举方式存留.**
 *
 * NeonMail-Premium
 * me.neon.mail.service.enums
 *
 * @author 老廖
 * @since 2024/1/17 3:50
 */
enum class TABStatement(val statement: String) {
    /**
     * 更新邮件领取状态
     */
    MAIL_UPDATE_STATE("UPDATE ${SQLImpl.mailTAB} SET `state`=?, `collectTimer`=? WHERE `uuid`=? LIMIT 1"),
    /**
     * 删除发送者与接收者均已标记删除的邮件
     */
    MAIL_DELETE_MARKED("DELETE FROM ${SQLImpl.mailTAB} WHERE `sd`=1 AND`td`=1"),
    /**
     * 新增邮件
     */
    MAIL_INSERT("INSERT INTO ${SQLImpl.mailTAB}(`uuid`,`sender`,`target`,`title`,`context`,`state`,`senderTimer`,`collectTimer`,`type`,`data`,`sd`,`td`) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)"),
    /**
     * 标记发送者已删除邮件
     */
    MAIL_SD_DELETE("UPDATE ${SQLImpl.mailTAB} SET `sd`=? WHERE `uuid`=? LIMIT 1"),
    /**
     * 标记接收者已删除邮件
     */
    MAIL_TD_DELETE("UPDATE ${SQLImpl.mailTAB} SET `td`=? WHERE `uuid`=? LIMIT 1")
}