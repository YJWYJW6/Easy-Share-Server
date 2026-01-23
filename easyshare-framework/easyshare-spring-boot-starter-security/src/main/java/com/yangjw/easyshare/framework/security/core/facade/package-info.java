/**
 * facade（门面/适配层）包
 *
 * <p>设计目的：
 * <ul>
 *   <li>为 framework-common/framework-mybatis 等底层模块提供“获取当前登录用户”的能力</li>
 *   <li>避免 framework-mybatis 直接依赖 framework-security，防止框架层耦合</li>
 * </ul>
 *
 * <p>实现方式：
 * <ul>
 *   <li>在本包中实现 common 模块定义的 LoginUserFacade 接口</li>
 *   <li>从 Spring Security 的 SecurityContextHolder 获取 CurrentLoginUser</li>
 *   <li>在应用启动时通过 LoginUserFacadeHolder.set(...) 注册实现</li>
 * </ul>
 *
 * <p>典型使用场景：
 * <ul>
 *   <li>MyBatis-Plus MetaObjectHandler 自动填充 creator/updater 字段</li>
 *   <li>业务框架层需要获取当前登录用户 id，但不希望引入 security 依赖</li>
 * </ul>
 */
package com.yangjw.easyshare.framework.security.core.facade;