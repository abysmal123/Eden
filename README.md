# Eden
 Java laboratory(maven project) with respect to 'Garden of Eden' in the field of cellular automata.
 # Java classes
 ```
 src/main/java
            ╰━━━━eden   // 无限配置满射问题
            │       ╰━━━━BuildTree.java                             // 由特定规则集作为根节点构造一棵树(ECA)   
            │       ╰━━━━GlobalSurjectivity.java                    // ECA无限配置满射问题 
            │       ╰━━━━GlobalSurjectivityDiameter4.java           // 规则直径4无限配置满射问题 
            │       ╰━━━━ShowProcedureTree.java                     // ECA二叉树的可视化 
            │       ╰━━━━ShowProcedureTreeDiameter4.java            // 规则直径4二叉树的可视化
            │       ╰━━━━TreeNode.java                              // (protected)树节点数据结构
            ╰━━━━finiteconfig   // 有限配置满射问题(王老师论文)
            │       ╰━━━━FiniteConfigD4.java                        // ECA有限配置满射问题
            │       ╰━━━━FiniteConfigECA.java                       // 规则直径4有限配置满射问题
            ╰━━━━periodic   // 循环边界满射问题
            │       ╰━━━━PeriodicD4FiniteLength.java                // 规则直径4循环边界满射问题
            │       ╰━━━━PeriodicD5FiniteLength.java                // 规则直径5循环边界满射问题
            │       ╰━━━━PeriodicECAFiniteLength.java               // ECA循环边界满射问题
            │       ╰━━━━ValueSet.java                              // (protected)堆节点数据结构
            ╰━━━━zeroboundary   // 零边界满射问题
            │       ╰━━━━TreeNode.java                              // (protected)树节点数据结构
            │       ╰━━━━ZeroBoundaryD4FiniteLength.java            // 规则直径4零边界满射问题
            │       ╰━━━━ZeroBoundaryD5FiniteLength.java            // 规则直径5零边界满射问题
            │       ╰━━━━ZeroBoundaryDiameter5.java                 // 规则直径5零边界满射问题（针对给定长度配置）
            │       ╰━━━━ZeroBoundaryECA.java                       // ECA零边界满射问题（针对给定长度配置）
            │       ╰━━━━ZeroBoundaryECAFiniteLength.java           // ECA零边界满射问题
            ╰━━━━(default package)  // 此目录下是一些测试文件，并非功能代码
            │       ╰━━━━TreeNode.java
            │       ╰━━━━ZeroBoundaryD4FiniteLength.java
            │       ╰━━━━ZeroBoundaryD5FiniteLength.java
            │       ╰━━━━ZeroBoundaryDiameter5.java
            │       ╰━━━━ZeroBoundaryECA.java
            │       ╰━━━━ZeroBoundaryECAFiniteLength.java                      ```