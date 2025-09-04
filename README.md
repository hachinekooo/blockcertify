# BlockcertifyApplication

基于 Spring Boot 开发的区块链存证系统，采用 AOP 实现无感接入，支持 API 调用。该系统脱胎于既往区块链功能接入经验，进行了全新设计与实现。

## 特性

- 🎯 **AOP 无感接入** - 通过注解标记需要存证的方法，零侵入业务代码
- 🔧 **多 SDK 适配** - 支持不同区块链 SDK，通过适配器模式统一调用
- 🔄 **完整状态管理** - 从提交到最终确认的完整状态流转
- 🛡️ **重试机制** - 智能重试策略，处理网络异常和区块重组
- 📊 **监控告警** - 支持状态监控和异常告警
- 🎨 **设计模式** - 使用策略、模板、适配器等模式，易于扩展

## 快速开始

### 添加依赖

```xml
<dependency>
    <groupId>com.github.blockcertify</groupId>
    <artifactId>blockcertify-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 配置

```yaml
blockchain:
  certification:
    enabled: true
    sdk-vendor: "example-chain"
    max-retry-count: 3
    required-confirmation: 6
```

### 使用示例



## 架构设计

### 核心组件

- **AOP 切面** - 拦截标记方法，提取存证数据
- **SDK 适配器** - 统一不同区块链 SDK 的调用方式
- **状态管理** - 完整的存证状态流转机制
- **数据提取器** - 支持不同业务类型的数据提取策略
- **重试调度** - 智能重试和状态检查

### 状态流转

```
INIT → SUBMITTING → PENDING → PACKED → CONFIRMED → FINALIZED
```

### 设计模式

- **适配器模式** - 兼容不同 SDK
- **策略模式** - 数据提取策略
- **模板方法** - 减少样板代码
- **管理器模式** - 统一管理映射处理器

## 文档

详细文档请参考：[论如何优雅的给系统接入区块链存证功能](https://hachinekooo.github.io/docs/project/blockchain.html)

## 贡献

欢迎提交 Issue 和 Pull Request！

## 开源协议

本项目采用 [GNU Affero General Public License v3.0 (AGPL-3.0)](LICENSE) 协议。

这意味着：
- ✅ 可以自由使用、修改和分发
- ✅ 可以用于商业项目
- ⚠️ 修改或衍生作品必须同样开源
- ⚠️ 网络服务也需要提供源代码
- ⚠️ 必须保持协议兼容性

如果你修改了本项目代码或基于本项目开发，请确保你的项目也采用兼容的开源协议。