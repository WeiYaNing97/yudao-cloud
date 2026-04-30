
package cn.iocoder.yudao.module.infra.service.codegen.inner;

import java.io.File;

/**
 * 模块脚手架生成器运行器
 * 
 * 使用方式：
 * 1. 直接运行 main 方法
 * 2. 传入参数：模块名称 模块描述 [端口号]
 * 
 * 示例：
 * java ModuleGeneratorRunner crm "CRM客户关系管理模块" 48083
 * 
 * @author 芋道源码
 */
public class ModuleGeneratorRunner {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("用法: java ModuleGeneratorRunner <模块名称> <模块描述> [端口号]");
            System.out.println("示例: java ModuleGeneratorRunner crm \"CRM客户关系管理模块\" 48083");
            return;
        }

        String moduleName = args[0];
        String description = args[1];
        Integer port = args.length > 2 ? Integer.parseInt(args[2]) : null;

        try {
            // 获取项目根目录（假设当前目录是项目根目录）
            String projectRoot = new File(".").getAbsolutePath();
            
            // 创建生成器
            ModuleGenerator generator = new ModuleGenerator();
            
            // 创建配置
            ModuleGenerator.ModuleConfig config = new ModuleGenerator.ModuleConfig(moduleName, description)
                    .port(port)
                    .generateDockerfile(true)
                    .generateTest(true);
            
            // 生成模块
            generator.generate(config, projectRoot);
            
            System.out.println("");
            System.out.println("========================================");
            System.out.println("模块生成成功！");
            System.out.println("模块名称: " + moduleName);
            System.out.println("模块描述: " + description);
            System.out.println("模块目录: " + projectRoot + File.separator + "yudao-module-" + moduleName);
            System.out.println("========================================");
            System.out.println("");
            System.out.println("生成的文件结构:");
            System.out.println("yudao-module-" + moduleName + "/");
            System.out.println("  ├── pom.xml");
            System.out.println("  ├── yudao-module-" + moduleName + "-api/");
            System.out.println("  │   ├── pom.xml");
            System.out.println("  │   └── src/main/java/cn/iocoder/yudao/module/" + moduleName + "/");
            System.out.println("  │       ├── package-info.java");
            System.out.println("  │       └── enums/ErrorCodeConstants.java");
            System.out.println("  └── yudao-module-" + moduleName + "-server/");
            System.out.println("      ├── pom.xml");
            System.out.println("      ├── Dockerfile");
            System.out.println("      └── src/main/");
            System.out.println("          ├── java/cn/iocoder/yudao/module/" + moduleName + "/");
            System.out.println("          │   ├── " + capitalize(moduleName) + "ServerApplication.java");
            System.out.println("          │   └── package-info.java");
            System.out.println("          └── resources/");
            System.out.println("              ├── application.yaml");
            System.out.println("              ├── application-dev.yaml");
            System.out.println("              ├── application-local.yaml");
            System.out.println("              └── logback-spring.xml");
            System.out.println("========================================");
            
        } catch (Exception e) {
            System.err.println("模块生成失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
