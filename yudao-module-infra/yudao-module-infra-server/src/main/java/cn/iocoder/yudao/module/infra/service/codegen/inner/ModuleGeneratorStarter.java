
package cn.iocoder.yudao.module.infra.service.codegen.inner;

/**
 * 模块生成器启动类
 * 
 * 使用方法：
 * 1. 修改下方的 MODULE_NAME 和 MODULE_DESCRIPTION 常量
 * 2. 右键点击此类 → Run 'ModuleGeneratorStarter.main()'
 * 3. 新模块会生成到项目根目录下
 * 
 * 配置说明：
 * - MODULE_NAME: 模块名称（小写，如：crm、erp、order）
 * - MODULE_DESCRIPTION: 模块描述
 * - PORT: 服务端口（可选，默认自动分配）
 * - OUTPUT_DIR: 输出目录（默认项目根目录）
 * 
 * @author 芋道源码
 */
public class ModuleGeneratorStarter {

    // ==================== 配置项 ====================
    /** 模块名称（小写，如：crm、erp、order） */
    private static final String MODULE_NAME = "order";
    
    /** 模块描述 */
    private static final String MODULE_DESCRIPTION = "订单模块";
    
    /** 服务端口（可选，null 表示自动分配） */
    private static final Integer PORT = 48099;
    
    /** 是否生成 Dockerfile */
    private static final boolean GENERATE_DOCKERFILE = true;
    
    /** 是否生成测试目录 */
    private static final boolean GENERATE_TEST = true;
    
    /** 输出目录（项目根目录） */
    private static final String OUTPUT_DIR = "d:/IdeaProjects/my/yudao-cloud";
    // ==================== 配置项结束 ====================

    public static void main(String[] args) {
        try {
            // 打印开始信息
            printBanner();
            
            // 创建生成器
            ModuleGenerator generator = new ModuleGenerator();
            
            // 创建配置
            ModuleGenerator.ModuleConfig config = new ModuleGenerator.ModuleConfig(MODULE_NAME, MODULE_DESCRIPTION)
                    .port(PORT)
                    .generateDockerfile(GENERATE_DOCKERFILE)
                    .generateTest(GENERATE_TEST);
            
            // 生成模块
            System.out.println("正在生成模块...");
            generator.generate(config, OUTPUT_DIR);
            
            // 打印成功信息
            printSuccess();
            
        } catch (Exception e) {
            // 打印失败信息
            printError(e);
            System.exit(1);
        }
    }

    private static void printBanner() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    模块脚手架生成器                              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("  模块名称:     " + MODULE_NAME);
        System.out.println("  模块描述:     " + MODULE_DESCRIPTION);
        System.out.println("  端口号:       " + (PORT != null ? PORT : "自动"));
        System.out.println("  输出目录:     " + OUTPUT_DIR);
        System.out.println();
    }

    private static void printSuccess() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                        模块生成成功！                            ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("  生成的模块结构:");
        System.out.println("    yudao-module-" + MODULE_NAME + "/");
        System.out.println("    ├── pom.xml");
        System.out.println("    ├── yudao-module-" + MODULE_NAME + "-api/");
        System.out.println("    │   └── src/main/java/cn/iocoder/yudao/module/" + MODULE_NAME + "/");
        System.out.println("    └── yudao-module-" + MODULE_NAME + "-server/");
        System.out.println("        ├── pom.xml");
        System.out.println("        ├── Dockerfile");
        System.out.println("        └── src/main/java/cn/iocoder/yudao/module/" + MODULE_NAME + "/");
        System.out.println();
        System.out.println("  输出目录: " + OUTPUT_DIR + "/yudao-module-" + MODULE_NAME);
        System.out.println();
    }

    private static void printError(Exception e) {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                        模块生成失败！                            ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("  错误信息: " + e.getMessage());
        System.out.println();
        e.printStackTrace();
        System.out.println();
    }
}
