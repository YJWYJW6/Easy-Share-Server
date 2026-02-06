/**
 * 基于 JSqlParser 解析 SQL，增加数据权限的 WHERE 条件
 * <p>
 * 整体设计：
 * <p>
 * ┌────────────┐
 * <p>
 * │ @DataPermission │  ← 控制是否开启、选择规则
 * <p>
 * └─────▲──────┘
 * <p>
 * AOP
 * <p>
 * ┌─────┴──────────────┐
 * <p>
 * │ DataPermissionContext │ ← ThreadLocal 存当前注解
 * <p>
 * └─────▲──────────────┘
 * <p>
 * ┌─────┴──────────────┐
 * <p>
 * │ MpDataPermissionHandler │ ← MP 插件入口
 * <p>
 * └─────▲──────────────┘
 * <p>
 * <p>
 * ┌─────┴──────────────┐
 * <p>
 * │ DataPermissionRule │ ← 规则接口（可扩容）
 * <p>
 * <p>
 * │   ├─ DeptRule
 * <p>
 * │   ├─ UserRule
 * <p>
 * │   └─ XxxRule
 * <p>
 * └────────────────────┘
 */
package com.yangjw.easyshare.framework.datapermission;
