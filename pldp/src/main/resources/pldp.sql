-- 2046xpro.com
-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 2020-07-06 11:25:19
-- 服务器版本： 10.1.30-MariaDB
-- PHP Version: 7.2.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pldp`
--

-- --------------------------------------------------------

--
-- 表的结构 `classes`
--

CREATE TABLE `classes` (
  `id` int(10) NOT NULL,
  `class_id` int(14) NOT NULL,
  `class_name` varchar(18) NOT NULL,
  `teacher_id` int(14) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `classes`
--

INSERT INTO `classes` (`id`, `class_id`, `class_name`, `teacher_id`) VALUES
(1, 10, '软件学院2016级10班', 1001),
(2, 11, '软件学院2016级1班', 1009),
(3, 12, '软件学院2016级3班', 1002),
(4, 13, '建环1班', 1001);

-- --------------------------------------------------------

--
-- 表的结构 `code_discuss`
--

CREATE TABLE `code_discuss` (
  `id` int(11) NOT NULL,
  `code_id` int(14) NOT NULL,
  `student_id` int(14) NOT NULL,
  `content` text NOT NULL,
  `create_time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `code_discuss`
--

INSERT INTO `code_discuss` (`id`, `code_id`, `student_id`, `content`, `create_time`) VALUES
(1, 1, 1000, '这道题比较11', '2020-01-30 00:00:00'),
(2, 1, 1001, '这道题这么做', '2020-02-15 17:55:03');

-- --------------------------------------------------------

--
-- 表的结构 `code_judgement`
--

CREATE TABLE `code_judgement` (
  `id` int(11) NOT NULL,
  `code_id` int(14) NOT NULL,
  `test_input` text NOT NULL,
  `test_output` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `code_judgement`
--

INSERT INTO `code_judgement` (`id`, `code_id`, `test_input`, `test_output`) VALUES
(1, 1, '1 1', '2'),
(2, 1, '2 4', '6'),
(4, 1, '3 3', '6'),
(5, 2, '1 3', '3'),
(6, 2, '2 6', '12'),
(7, 4, '5 2', '3'),
(8, 4, '3 2', '1'),
(9, 5, '', '5050'),
(10, 6, '1 2 3', '3'),
(11, 6, '11 14 15', '15'),
(12, 6, '14 12 11', '14');

-- --------------------------------------------------------

--
-- 表的结构 `code_question`
--

CREATE TABLE `code_question` (
  `id` int(11) NOT NULL,
  `course_id` int(14) NOT NULL,
  `title` varchar(30) NOT NULL,
  `question_text` text NOT NULL,
  `degree` int(2) NOT NULL,
  `submit_num` int(10) NOT NULL,
  `correct_num` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `code_question`
--

INSERT INTO `code_question` (`id`, `course_id`, `title`, `question_text`, `degree`, `submit_num`, `correct_num`) VALUES
(1, 101, 'a+b', '求a+b', 2, 12, 10),
(2, 101, 'a*b', '求a*b', 1, 4, 2),
(4, 101, '求a-b', '求a-b', 1, 0, 0),
(5, 101, '1-100求和', '求1-100所有整数之和', 2, 0, 0),
(6, 101, '三个数中最大', '编写一个程序，求三个数中最大的一个', 2, 23, 11);

-- --------------------------------------------------------

--
-- 表的结构 `code_submit`
--

CREATE TABLE `code_submit` (
  `id` int(10) NOT NULL,
  `input` text NOT NULL,
  `time` datetime NOT NULL,
  `code_id` int(14) NOT NULL,
  `student_id` int(14) NOT NULL,
  `state` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `code_submit`
--

INSERT INTO `code_submit` (`id`, `input`, `time`, `code_id`, `student_id`, `state`) VALUES
(1, 'import java.util.*;\npublic class Main{\n\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n		System.out.println(a+b);\n	}\n}', '2020-02-21 19:35:01', 1, 1004, 1),
(2, 'import java.util.*;\npublic class Main{\n\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n		System.out.println(a+b);\n	}\n}', '2020-04-12 18:34:46', 1, 1004, 1),
(3, 'import java.util.*;\npublic class Main{\n\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n		System.out.println(a+b);\n	}\n}', '2020-04-12 18:34:51', 1, 1004, 1),
(4, 'import java.util.*;\npublic class Main{\n\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n		System.out.println(a+b);\n	}\n}', '2020-04-12 18:34:57', 1, 1004, 1),
(5, 'import java.util.*;\npublic class Main{\n\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n		System.out.println(a+b);\n	}\n}', '2020-04-12 18:35:12', 2, 1004, 0),
(6, 'import java.util.*;\npublic class Main{\n\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n		System.out.println(a+b);\n	}\n}', '2020-04-12 18:35:49', 1, 1004, 1),
(7, 'import java.util.*;\npublic class Main{\n\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n		System.out.println(a+b);\n	}\n}', '2020-04-12 18:36:36', 1, 1004, 1),
(8, 'import java.util.*;\npublic class Main{\n\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n		System.out.println(a+b);\n	}\n}', '2020-04-12 18:36:53', 1, 1004, 1),
(9, 'import java.util.*;\npublic class Main{\n\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n		System.out.println(a-b);\n	}\n}', '2020-04-12 18:37:53', 1, 1004, 0),
(10, 'import java.util.*;\npublic class Main{\n\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n		System.out.println(a+b);\n	}\n}', '2020-04-21 22:54:35', 2, 1006, 0),
(11, 'import java.util.*;\npublic class Main{\n\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n		System.out.println(a*b);\n	}\n}', '2020-04-21 22:54:50', 2, 1006, 1),
(12, 'import java.util.*;\npublic class Main{\n\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n		System.out.println(a*b);\n	}\n}', '2020-04-21 22:55:08', 2, 1006, 1),
(13, 'import java.util.*;\npublic class Main{\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n                int c = sc.nextInt();\n		int max = a;\n		if(b>max)\n		 max = b;\n		if(c>max)\n		 max = c;\n		System.out.println(max);\n	}\n}', '2020-05-19 15:02:01', 6, 1004, 1),
(14, 'import java.util.*;\npublic class Main{\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n                int c = sc.nextInt();\n		int max = a;\n		if(b>max)\n		 max = b;\n		if(c>max)\n		 max = c;\n		System.out.println(c);\n	}\n}', '2020-05-19 15:02:10', 6, 1004, 1),
(15, 'import java.util.*;\npublic class Main{\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n                int c = sc.nextInt();\n		int max = a;\n		if(b>max)\n		 max = b;\n		if(c>max)\n		 max = c;\n		System.out.println(c);\n	}\n}', '2020-05-19 15:02:35', 6, 1004, 0.6666666666666666),
(16, 'import java.util.*;\npublic class Main{\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n                int c = sc.nextInt();\n		int max = a;\n		if(b>max)\n		 max = b;\n		if(c>max)\n		 max = c;\n		System.out.println(max);\n	}\n}', '2020-05-19 15:02:59', 6, 1004, 1),
(17, 'import java.util.*;\npublic class Main{\n\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n                int c = sc.nextInt();\n		int max = a;\n		if(b>max)\n		 max = b;\n		if(c>max)\n		 max = c;\n		System.out.println(max);\n	}\n}', '2020-05-19 15:18:23', 6, 1004, 1),
(18, 'import java.util.*;\npublic class Main{\n\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n                int c = sc.nextInt();\n		int max = a;\n		if(b>max)\n		 max = b;\n		if(c>max)\n		 max = c;\n		System.out.println(c);\n	}\n}', '2020-05-19 15:18:35', 6, 1004, 0.6666666666666666),
(19, 'import java.util.*;\npublic class Main{\n\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n                int c = sc.nextInt();\n		int max = a;\n		if(b>max)\n		 max = b;\n		if(c>max)\n		 max = c;\n		System.out.println\n	}\n}', '2020-05-19 15:18:46', 6, 1004, 0),
(20, 'import java.util.*;\npublic class Main{\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n                int c = sc.nextInt();\n		int max = a;\n		if(b>max)\n		 max = b;\n		if(c>max)\n		 max = c;\n		System.out.println(max);\n	}\n}', '2020-05-19 15:25:31', 6, 1004, 1),
(21, 'import java.util.*;\npublic class Main{\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n                int c = sc.nextInt();\n		int max = a;\n		if(b>max)\n		 max = b;\n		if(c>max)\n		 max = c;\n		System.out.println(c);\n	}\n}', '2020-05-19 15:25:42', 6, 1004, 0.6666666666666666),
(22, 'import java.util.*;\npublic class Main{\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n                int c = sc.nextInt();\n		int max = a;\n		if(b>max)\n		 max = b;\n		if(c>max)\n		 max = c;\n		System.out.println\n	}\n}', '2020-05-19 15:25:55', 6, 1004, 0),
(23, 'import java.util.*;\npublic class Main{\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n                int c = sc.nextInt();\n		int max = a;\n		if(b>max)\n		 max = b;\n		if(c>max)\n		 max = c;\n		System.out.println(max);\n	}\n}', '2020-05-19 15:34:31', 6, 1004, 1),
(24, 'import java.util.*;\npublic class Main{\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n                int c = sc.nextInt();\n		int max = a;\n		if(b>max)\n		 max = b;\n		if(c>max)\n		 max = c;\n		System.out.println(C);\n	}\n}', '2020-05-19 15:34:42', 6, 1004, 0),
(25, 'import java.util.*;\npublic class Main{\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n                int c = sc.nextInt();\n		int max = a;\n		if(b>max)\n		 max = b;\n		if(c>max)\n		 max = c;\n		System.out.println(max);\n	}\n}', '2020-05-19 15:35:37', 6, 1004, 1),
(26, 'import java.util.*;\npublic class Main{\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n                int c = sc.nextInt();\n		int max = a;\n		if(b>max)\n		 max = b;\n		if(c>max)\n		 max = c;\n		System.out.println(c);\n	}\n}', '2020-05-19 15:35:48', 6, 1004, 0.6666666666666666),
(27, 'import java.util.*;\npublic class Main{\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n                int c = sc.nextInt();\n		int max = a;\n		if(b>max)\n		 max = b;\n		if(c>max)\n		 max = c;\n		System.out.println\n	}\n}', '2020-05-19 15:35:59', 6, 1004, 0),
(28, 'import java.util.*;\npublic class Main{\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n                int c = sc.nextInt();\n		int max = a;\n		if(b>max)\n		 max = b;\n		if(c>max)\n		 max = c;\n		System.out.println(max);\n	}\n}', '2020-05-19 15:37:58', 6, 1004, 1),
(29, 'import java.util.*;\npublic class Main{\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n                int c = sc.nextInt();\n		int max = a;\n		if(b>max)\n		 max = b;\n		if(c>max)\n		 max = c;\n		System.out.println(c);\n	}\n}', '2020-05-19 15:38:08', 6, 1004, 0.6666666666666666),
(30, 'import java.util.*;\npublic class Main{\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n                int c = sc.nextInt();\n		int max = a;\n		if(b>max)\n		 max = b;\n		if(c>max)\n		 max = c;\n		System.out.println\n	}\n}', '2020-05-19 15:38:20', 6, 1004, 0),
(31, 'import java.util.*;\npublic class Main{\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n                int c = sc.nextInt();\n		int max = a;\n		if(b>max)\n		 max = b;\n		if(c>max)\n		 max = c;\n		System.out.println(max);\n	}\n}', '2020-05-19 15:40:36', 6, 1004, 1),
(32, 'import java.util.*;\npublic class Main{\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n                int c = sc.nextInt();\n		int max = a;\n		if(b>max)\n		 max = b;\n		if(c>max)\n		 max = c;\n		System.out.println(max);\n	}\n}', '2020-05-19 15:41:04', 6, 1004, 1),
(33, 'import java.util.*;\npublic class Main{\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n                int c = sc.nextInt();\n		int max = a;\n		if(b>max)\n		 max = b;\n		if(c>max)\n		 max = c;\n		System.out.println(max);\n	}\n}', '2020-05-19 15:41:47', 6, 1004, 1),
(34, 'import java.util.*;\npublic class Main{\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n                int c = sc.nextInt();\n		int max = a;\n		if(b>max)\n		 max = b;\n		if(c>max)\n		 max = c;\n		System.out.println(c);\n	}\n}', '2020-05-19 15:41:57', 6, 1004, 0.6666666666666666),
(35, 'import java.util.*;\npublic class Main{\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n                int c = sc.nextInt();\n		int max = a;\n		if(b>max)\n		 max = b;\n		if(c>max)\n		 max = c;\n		System.out.println\n	}\n}', '2020-05-19 15:42:08', 6, 1004, 0);

-- --------------------------------------------------------

--
-- 表的结构 `courses`
--

CREATE TABLE `courses` (
  `id` int(11) NOT NULL,
  `course_id` int(14) NOT NULL,
  `course_type` int(3) NOT NULL,
  `course_name` varchar(18) NOT NULL,
  `teacher_id` int(14) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `courses`
--

INSERT INTO `courses` (`id`, `course_id`, `course_type`, `course_name`, `teacher_id`) VALUES
(1, 101, 2, '面向对象程序设计导论(Java)', 1002),
(2, 102, 1, '程序设计导论(C)', 1004),
(3, 103, 3, '深入学习Python', 1001),
(4, 104, 2, 'Java并发编程', 1001);

-- --------------------------------------------------------

--
-- 表的结构 `course_content`
--

CREATE TABLE `course_content` (
  `id` int(11) NOT NULL,
  `course_id` int(14) NOT NULL,
  `chapter` varchar(10) NOT NULL,
  `section` varchar(10) NOT NULL,
  `title` text NOT NULL,
  `content` text NOT NULL,
  `exercise_id` int(14) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `course_content`
--

INSERT INTO `course_content` (`id`, `course_id`, `chapter`, `section`, `title`, `content`, `exercise_id`) VALUES
(1, 101, '1', '0', 'Java初体验', '本章节主要讲解 Java 目前的应用、如何搭建 Java 开发环境、以及如何使用工具进行 Java 程序的开发，为以后的学习打下基础', 0),
(2, 101, '1', '1', 'Java中的关键字', 'Java 语言中有一些具有特殊用途的词被称为关键字。关键字对 Java 的编译器有着特殊的意义，在程序中应用时一定要慎重哦！！\nJava 中常用关键字：\nstatic、extends、super、final、abstract、interface、implements。', 5),
(3, 101, '1', '2', '认识Java标识符', ' 问：标识符是神马？\r\n  答：标识符就是用于给 Java 程序中变量、类、方法等命名的符号。\r\n使用标识符时，需要遵守几条规则：\r\n 1.  标识符可以由字母、数字、下划线（_）、美元符（$）组成，但不能包含 @、%、空格等其它特殊字符，不能以数字开头。譬如：123name 就是不合法滴\r\n 2.  标识符不能是 Java 关键字和保留字（ Java 预留的关键字，以后的升级版本中有可能作为关键字），但可以包含关键字和保留字。如：不可以使用 void 作为标识符，但是 Myvoid 可以\r\n 3.  标识符是严格区分大小写的。 所以涅，一定要分清楚 imooc 和 IMooc 是两个不同的标识符哦！\r\n 4.  标识符的命名最好能反映出其作用，做到见名知意。', 0),
(4, 101, '1', '3', 'Java中的数据类型', '通常情况下，为了方便物品的存储，我们会规定每个盒子可以存放的物品种类，就好比在“放臭袜子的盒子”里我们是不会放“面包”的！同理，变量的存储也讲究“分门别类”！\n\nJava 语言是一种强类型语言。通俗点说就是，在 Java 中存储的数据都是有类型的，而且必须在编译时就确定其类型。 Java 中有两类数据类型，基本数据类型和引用数据类型。\n在 Java 的领域里，基本数据类型变量存的是数据本身，而引用类型变量存的是保存数据的空间地址。说白了，基本数据类型变量里存储的是直接放在抽屉里的东西，而引用数据类型变量里存储的是这个抽屉的钥匙，钥匙和抽屉一一对应。\nString 是一种常见的引用数据类型，用来表示字符串。在程序开发中，很多操作都要使用字符串来完成，例如系统中的用户名、密码、电子邮箱等。\n', 1),
(7, 101, '1', '4', 'Java常量的应用', '所谓常量，我们可以理解为是一种特殊的变量，它的值被设定后，在程序运行过程中不允许改变。\n\n语法：final 常量名 = 值;\n\n程序中使用常量可以提高代码的可维护性。例如，在项目开发时，我们需要指定用户的性别，此时可以定义一个常量 SEX，赋值为 \"男\"，在需要指定用户性别的地方直接调用此常量即可，避免了由于用户的不规范赋值导致程序出错的情况。\n\n伙计们注意啦：常量名一般使用大写字符', 0),
(8, 101, '2', '0', '常用的运算符', 'Java 提供了一套丰富的运算符来操纵变量。学完这一章节后您将会理解并能够灵活的使用各种运算符', 0),
(9, 101, '2', '1', 'Java中的算术运算符', '算术运算符主要用于进行基本的算术运算，如加法、减法、乘法、除法等。\nJava中 常用运算符：+、-、*、/、%、++、--。\n', 0),
(10, 101, '2', '2', 'Java中的赋值运算符', '赋值运算符是指为变量或常量指定数值的符号。如可以使用 “=” 将右边的表达式结果赋给左边的操作数。\n\nJava 支持的常用赋值运算符如下：=、+=、-=、*=、/=、%=。', 0),
(11, 101, '2', '3', 'Java中的比较运算符', '比较运算符用于判断两个数据的大小，例如：大于、等于、不等于。比较的结果是一个布尔值（ true 或 false ）。\nJava 中常用的比较运算符如下：>、<、>=、<=、==、!=。', 0),
(12, 101, '3', '0', '流程控制语句', '本章节主要讲解 Java 中的流程控制语句，包括选择结构、循环结构、跳转语句等。学完这一章后将对程序执行流程有更深的理解，会使用流程控制语句控制程序代码执行的过程', 0),
(13, 101, '4', '0', '数组', '本章节主要讲解 Java 中的数组，包括数组的创建与使用、使用 Arrays 类操作数组、二维数组的使用。学完这一章后将能够熟练使用数组进行数据的存储和操作', 0),
(14, 101, '5', '0', '方法', '本章节主要讲解 Java 中的方法，包括方法的定义、方法的调用、参数传递和方法重载等。学完这一章后将对方法的概念有深层次的理解，并能够通过定义方法来实现独立的功能', 0),
(15, 101, '3', '1', 'Java条件语句之 if', '生活中，我们经常需要先做判断，然后才决定是否要做某件事情。例如，如果考试成绩大于 90 分，则奖励一个 IPHONE 5S 。对于这种“需要先判断条件，条件满足后才执行的情况”，就可以使用 if 条件语句实现。', 0),
(16, 101, '4', '1', '什么是数组', '问：编写代码保存 4 名学生的考试成绩。\n\n答：简单啊，定义 4 个变量呗\n\n问：那“计算全年级 400 名学生的考试成绩”，肿么办\n\n答： 。。。。。。。\n\n数组，就可以帮助你妥妥的解决问题啦！！\n\n数组可以理解为是一个巨大的“盒子”，里面可以按顺序存放多个类型相同的数据，比如可以定义 int 型的数组 scores 存储 4 名学生的成绩\n数组中的元素都可以通过下标来访问，下标从 0 开始。例如，可以通过 scores[0] 获取数组中的第一个元素 76 ，scores[2] 就可以取到第三个元素 92 啦！', 0),
(17, 101, '5', '1', '如何定义 Java 中的方法', '所谓方法，就是用来解决一类问题的代码的有序组合，是一个功能模块。\n\n一般情况下，定义一个方法的语法是：访问修饰符、返回值类型、方法名（参数列表）、方法体。\n其中：\n\n1、 访问修饰符：方法允许被访问的权限范围， 可以是 public、protected、private 甚至可以省略 ，其中 public 表示该方法可以被其他任何代码调用，其他几种修饰符的使用在后面章节中会详细讲解滴\n\n2、 返回值类型：方法返回值的类型，如果方法不返回任何值，则返回值类型指定为 void ；如果方法具有返回值，则需要指定返回值的类型，并且在方法体中使用 return 语句返回值\n\n3、 方法名：定义的方法的名字，必须使用合法的标识符\n\n4、 参数列表：传递给方法的参数列表，参数可以有多个，多个参数间以逗号隔开，每个参数由参数类型和参数名组成，以空格隔开 \n\n根据方法是否带参、是否带返回值，可将方法分为四类：\n\nØ 无参无返回值方法\n\nØ 无参带返回值方法\n\nØ 带参无返回值方法\n\nØ 带参带返回值方法', 0);

-- --------------------------------------------------------

--
-- 表的结构 `discuss_comment`
--

CREATE TABLE `discuss_comment` (
  `id` int(10) NOT NULL,
  `content` text NOT NULL,
  `user_type` int(2) NOT NULL,
  `user_id` int(14) NOT NULL,
  `user_name` varchar(18) NOT NULL,
  `entity_type` int(2) NOT NULL,
  `entity_id` int(10) NOT NULL,
  `create_time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `discuss_comment`
--

INSERT INTO `discuss_comment` (`id`, `content`, `user_type`, `user_id`, `user_name`, `entity_type`, `entity_id`, `create_time`) VALUES
(1, '我觉得不错', 1, 1003, '土豆', 1, 1, '2020-02-06 11:00:00'),
(2, '你说的都对', 2, 1001, '李四', 1, 1, '2020-02-09 16:30:53'),
(3, '你说的都对', 2, 1001, '李四', 1, 1, '2020-02-09 16:30:57'),
(4, '我再试试', 2, 1001, '李四', 1, 1, '2020-02-09 16:42:14'),
(5, '重新试下', 2, 1001, '李四', 1, 1, '2020-02-09 16:48:09'),
(6, '我不相信', 2, 1001, '李四', 1, 1, '2020-02-09 16:49:01'),
(7, '最后尝试一下', 2, 1001, '李四', 1, 1, '2020-02-09 16:52:30'),
(8, '回答下试试', 2, 1002, '王菲', 1, 1, '2020-02-09 18:01:39'),
(9, '我知道啊', 1, 1001, '西蓝花', 1, 5, '2020-02-14 16:14:27');

-- --------------------------------------------------------

--
-- 表的结构 `discuss_question`
--

CREATE TABLE `discuss_question` (
  `id` int(10) NOT NULL,
  `title` text NOT NULL,
  `content` text NOT NULL,
  `user_type` int(2) NOT NULL,
  `user_id` int(14) NOT NULL,
  `user_name` varchar(18) NOT NULL,
  `create_time` datetime NOT NULL,
  `comment_count` int(10) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `discuss_question`
--

INSERT INTO `discuss_question` (`id`, `title`, `content`, `user_type`, `user_id`, `user_name`, `create_time`, `comment_count`) VALUES
(1, '你好', '嘿你好吗，这是个测试', 1, 1000, '肖明', '2020-02-06 10:00:00', 8),
(3, '你好3', '你说怎么样啊444', 1, 1000, '肖明', '2020-02-06 12:00:00', 0),
(4, '你好4', '你说怎么样啊2', 1, 1000, '肖明', '2020-02-06 15:00:00', 0),
(5, '哈哈啊', '我是学生', 2, 1001, '李四', '2020-02-09 14:37:31', 1);

-- --------------------------------------------------------

--
-- 表的结构 `exam_answerinfo`
--

CREATE TABLE `exam_answerinfo` (
  `id` int(10) NOT NULL,
  `student_id` int(14) NOT NULL,
  `paper_id` int(10) NOT NULL,
  `paper_type` int(3) NOT NULL,
  `type` int(2) NOT NULL,
  `question_id` int(14) NOT NULL,
  `answer` text NOT NULL,
  `correct_answer` text NOT NULL,
  `score` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `exam_answerinfo`
--

INSERT INTO `exam_answerinfo` (`id`, `student_id`, `paper_id`, `paper_type`, `type`, `question_id`, `answer`, `correct_answer`, `score`) VALUES
(11, 1004, 16, 1, 3, 1, 'import java.util.*;\r\npublic class Main{\r\n	public Integer Solution(Integer a,Integer b){\r\n		return a+b;\r\n	}\r\n\r\n}', '', 10),
(12, 1004, 16, 1, 3, 2, 'import java.util.*;\r\npublic class Main{\r\n	public Integer Solution(Integer a,Integer b){\r\n		return a+b;\r\n	}\r\n\r\n}', '', 0),
(13, 1004, 16, 1, 2, 1, '3', '3', 5),
(14, 1004, 16, 1, 2, 2, '4', '4', 5),
(15, 1004, 16, 1, 2, 5, '2', 'service', 0),
(16, 1004, 16, 1, 1, 1, 'B', 'A', 0),
(17, 1004, 16, 1, 1, 2, 'C', 'B', 0),
(18, 1004, 16, 1, 1, 4, 'A', 'C', 0),
(19, 1004, 16, 1, 1, 5, 'C', 'D', 0),
(20, 1004, 16, 1, 1, 7, 'B', 'D', 0),
(21, 1004, 14, 2, 1, 1, 'B', 'A', 0),
(22, 1004, 14, 2, 1, 2, 'C', 'B', 0),
(23, 1004, 14, 2, 1, 4, 'C', 'C', 2),
(24, 1004, 14, 2, 1, 5, 'C', 'D', 0),
(25, 1004, 14, 2, 1, 8, 'B', 'A', 0),
(26, 1004, 14, 2, 2, 1, '3', '3', 5),
(27, 1004, 14, 2, 2, 6, '1', '一致性', 0),
(28, 1004, 14, 2, 2, 7, '24', 'DispatcherServlet', 0),
(29, 1004, 14, 2, 3, 4, 'import java.util.*;\r\npublic class Main{\r\n	public Integer Solution(Integer a,Integer b,Integer c){\r\n		return a+b-c;\r\n	}\r\n\r\n}', '', 0),
(30, 1006, 22, 1, 1, 1, 'A', 'A', 5),
(31, 1006, 22, 1, 1, 4, 'C', 'C', 5),
(32, 1006, 22, 1, 1, 7, 'D', 'D', 5),
(33, 1006, 22, 1, 2, 1, '3', '3', 8),
(34, 1006, 22, 1, 2, 6, '一致性', '一致性', 8),
(35, 1006, 22, 1, 2, 7, 'DispacherServlet', 'DispatcherServlet', 0),
(36, 1006, 22, 1, 3, 2, '', '', 10),
(37, 1006, 22, 1, 3, 3, '', '', 10),
(38, 1006, 23, 2, 1, 2, 'B', 'B', 5),
(39, 1006, 23, 2, 1, 3, 'D', 'D', 5),
(40, 1006, 23, 2, 1, 6, 'A', 'A', 5),
(41, 1006, 23, 2, 1, 7, 'D', 'D', 5),
(42, 1006, 23, 2, 1, 8, 'A', 'A', 5),
(43, 1006, 23, 2, 2, 4, '', 'read-only=true', 0),
(44, 1006, 23, 2, 2, 5, '', 'service', 0),
(45, 1006, 23, 2, 2, 7, '', 'DispatcherServlet', 0),
(46, 1006, 23, 2, 3, 1, 'import java.util.*;\npublic class Main{\n\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n		System.out.println(a+b);\n	}\n}', '', 20),
(47, 1006, 23, 2, 3, 2, 'import java.util.*;\npublic class Main{\n\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n		System.out.println(a*b);\n	}\n}', '', 20),
(48, 1004, 25, 1, 1, 1, 'B', 'A', 0),
(49, 1004, 25, 1, 1, 2, 'C', 'B', 0),
(50, 1004, 25, 1, 1, 5, 'A', 'D', 0),
(51, 1004, 25, 1, 1, 6, 'A', 'A', 5),
(52, 1004, 25, 1, 1, 7, 'A', 'D', 0),
(53, 1004, 26, 1, 1, 2, 'C', 'B', 0),
(54, 1004, 26, 1, 1, 3, 'A', 'D', 0),
(55, 1004, 26, 1, 1, 5, 'C', 'D', 0),
(56, 1004, 26, 1, 1, 7, 'A', 'D', 0),
(57, 1004, 26, 1, 1, 8, 'A', 'A', 5),
(58, 1004, 26, 1, 2, 1, '', '3', 0),
(59, 1004, 26, 1, 2, 5, '', 'service', 0),
(60, 1004, 26, 1, 2, 7, '', 'DispatcherServlet', 0),
(61, 1004, 26, 1, 3, 2, '', '', 0),
(62, 1004, 26, 1, 3, 3, '', '', 0),
(63, 1004, 29, 1, 1, 1, 'A', 'A', 5),
(64, 1004, 29, 1, 1, 3, 'B', 'D', 0),
(65, 1004, 29, 1, 1, 5, 'D', 'D', 5),
(66, 1004, 29, 1, 1, 6, 'B', 'A', 0),
(67, 1004, 29, 1, 1, 7, 'A', 'D', 0),
(68, 1004, 29, 1, 2, 4, '', 'read-only=true', 0),
(69, 1004, 29, 1, 2, 6, '', '一致性', 0),
(70, 1004, 29, 1, 2, 7, '', 'DispatcherServlet', 0),
(71, 1004, 29, 1, 3, 1, '', '', 0),
(72, 1004, 29, 1, 3, 4, '', '', 0);

-- --------------------------------------------------------

--
-- 表的结构 `exam_blank_group`
--

CREATE TABLE `exam_blank_group` (
  `id` int(10) NOT NULL,
  `fill_blank_id` int(10) NOT NULL,
  `paper_id` int(10) NOT NULL,
  `paper_type` int(3) NOT NULL,
  `score` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `exam_blank_group`
--

INSERT INTO `exam_blank_group` (`id`, `fill_blank_id`, `paper_id`, `paper_type`, `score`) VALUES
(3, 1, 10, 1, 5),
(4, 2, 10, 1, 5),
(5, 6, 10, 1, 5),
(6, 2, 11, 2, 5),
(7, 6, 11, 2, 5),
(8, 7, 11, 2, 5),
(9, 1, 12, 1, 2),
(10, 5, 12, 1, 2),
(11, 6, 12, 1, 2),
(15, 1, 14, 2, 5),
(16, 6, 14, 2, 5),
(17, 7, 14, 2, 5),
(18, 1, 16, 1, 5),
(19, 2, 16, 1, 5),
(20, 5, 16, 1, 5),
(21, 1, 20, 1, 5),
(22, 4, 20, 1, 5),
(23, 7, 20, 1, 5),
(24, 1, 21, 2, 5),
(25, 5, 21, 2, 5),
(26, 7, 21, 2, 5),
(27, 1, 22, 1, 8),
(28, 6, 22, 1, 8),
(29, 7, 22, 1, 8),
(30, 4, 23, 2, 10),
(31, 5, 23, 2, 10),
(32, 7, 23, 2, 10),
(36, 1, 25, 1, 5),
(37, 2, 25, 1, 5),
(38, 5, 25, 1, 5),
(39, 1, 26, 1, 5),
(40, 5, 26, 1, 5),
(41, 7, 26, 1, 5),
(42, 2, 27, 1, 5),
(43, 4, 27, 1, 5),
(44, 5, 27, 1, 5),
(45, 2, 28, 1, 5),
(46, 4, 28, 1, 5),
(47, 5, 28, 1, 5),
(48, 4, 29, 1, 5),
(49, 6, 29, 1, 5),
(50, 7, 29, 1, 5);

-- --------------------------------------------------------

--
-- 表的结构 `exam_code_group`
--

CREATE TABLE `exam_code_group` (
  `id` int(11) NOT NULL,
  `code_or_exercise_id` int(11) NOT NULL,
  `paper_id` int(11) NOT NULL,
  `paper_type` int(3) NOT NULL,
  `score` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `exam_code_group`
--

INSERT INTO `exam_code_group` (`id`, `code_or_exercise_id`, `paper_id`, `paper_type`, `score`) VALUES
(2, 4, 10, 1, 5),
(3, 4, 11, 2, 10),
(4, 4, 12, 1, 2),
(6, 4, 14, 2, 10),
(7, 1, 16, 1, 10),
(8, 2, 16, 1, 10),
(9, 1, 20, 1, 10),
(10, 4, 20, 1, 10),
(11, 3, 21, 2, 10),
(12, 4, 21, 2, 10),
(13, 2, 22, 1, 10),
(14, 3, 22, 1, 10),
(15, 1, 23, 2, 20),
(16, 2, 23, 2, 20),
(19, 1, 25, 1, 10),
(20, 5, 25, 1, 10),
(21, 2, 26, 1, 10),
(22, 3, 26, 1, 10),
(23, 2, 27, 1, 10),
(24, 5, 27, 1, 10),
(25, 2, 28, 1, 10),
(26, 5, 28, 1, 10),
(27, 1, 29, 1, 10),
(28, 4, 29, 1, 10);

-- --------------------------------------------------------

--
-- 表的结构 `exam_paperinfo`
--

CREATE TABLE `exam_paperinfo` (
  `id` int(10) NOT NULL,
  `course_id` int(14) NOT NULL,
  `chapter` varchar(10) NOT NULL,
  `class_id` int(14) NOT NULL,
  `type` int(2) NOT NULL,
  `single_count` int(3) NOT NULL,
  `single_score` int(3) NOT NULL,
  `blank_count` int(3) NOT NULL,
  `blank_score` int(3) NOT NULL,
  `code_count` int(3) NOT NULL,
  `code_score` int(3) NOT NULL,
  `create_time` datetime NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `paper_name` text NOT NULL,
  `teacher_id` int(14) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `exam_paperinfo`
--

INSERT INTO `exam_paperinfo` (`id`, `course_id`, `chapter`, `class_id`, `type`, `single_count`, `single_score`, `blank_count`, `blank_score`, `code_count`, `code_score`, `create_time`, `start_time`, `end_time`, `paper_name`, `teacher_id`) VALUES
(10, 101, '0', 10, 2, 5, 5, 3, 5, 1, 5, '2020-02-12 21:27:00', '2020-02-13 10:00:00', '2020-02-13 12:00:00', 'java第一次考试', 1001),
(11, 101, '1', 10, 1, 4, 5, 3, 5, 1, 10, '2020-02-12 21:28:14', '2020-02-12 00:00:00', '2020-02-25 00:00:00', 'java第一次作业', 1001),
(12, 101, '1', 10, 1, 3, 5, 3, 2, 1, 2, '2020-02-12 21:45:00', '2020-02-12 00:00:00', '2020-02-24 00:00:00', 'java第二次作业', 1001),
(14, 101, '0', 12, 2, 5, 2, 3, 5, 1, 10, '2020-02-17 20:10:22', '2020-02-11 00:00:00', '2020-02-27 00:00:00', 'java第一次期中考试', 1002),
(16, 101, '1', 12, 1, 5, 3, 3, 5, 2, 10, '2020-02-19 17:35:04', '2020-02-06 00:00:00', '2020-02-25 00:00:00', 'java第二次作业', 1002),
(20, 101, '1', 12, 1, 2, 5, 3, 5, 2, 10, '2020-02-20 10:44:26', '2020-02-13 00:00:00', '2020-02-29 11:15:00', 'java第三次作业', 1002),
(21, 101, '0', 12, 2, 4, 5, 3, 5, 2, 10, '2020-02-21 19:44:00', '2020-02-18 00:00:00', '2020-02-27 00:00:00', 'java期末考试', 1002),
(22, 101, '1', 12, 1, 3, 5, 3, 8, 2, 10, '2020-03-02 16:02:37', '2020-02-19 00:00:00', '2020-03-03 00:00:00', 'java期末作业', 1002),
(23, 101, '0', 12, 2, 5, 5, 3, 10, 2, 20, '2020-03-02 16:28:16', '2020-03-01 00:00:00', '2020-03-03 00:00:00', 'java3/4学期考试', 1002),
(25, 101, '1', 12, 1, 5, 5, 3, 5, 2, 10, '2020-05-19 15:19:45', '2020-05-18 00:00:00', '2020-05-20 00:00:00', 'Java第4次作业', 1002),
(26, 101, '1', 12, 1, 5, 5, 3, 5, 2, 10, '2020-05-19 15:26:41', '2020-05-17 00:00:00', '2020-05-22 00:00:00', 'java第5次作业', 1002),
(27, 101, '1', 12, 1, 5, 5, 3, 5, 2, 10, '2020-05-19 15:36:43', '2020-05-18 00:00:00', '2020-05-23 00:00:00', 'java第6次作业', 1002),
(28, 101, '1', 12, 1, 5, 5, 3, 5, 2, 10, '2020-05-19 15:39:04', '2020-05-18 00:00:00', '2020-05-25 00:00:00', 'java第7次作业', 1002),
(29, 101, '1', 12, 1, 5, 5, 3, 5, 2, 10, '2020-05-19 15:42:46', '2020-05-18 00:00:00', '2020-05-26 00:00:00', 'java第8次作业', 1002);

-- --------------------------------------------------------

--
-- 表的结构 `exam_scoreinfo`
--

CREATE TABLE `exam_scoreinfo` (
  `id` int(10) NOT NULL,
  `student_id` int(14) NOT NULL,
  `paper_id` int(10) NOT NULL,
  `paper_type` int(3) NOT NULL,
  `start_time` datetime DEFAULT NULL,
  `state` int(2) DEFAULT '0',
  `score` int(3) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `exam_scoreinfo`
--

INSERT INTO `exam_scoreinfo` (`id`, `student_id`, `paper_id`, `paper_type`, `start_time`, `state`, `score`) VALUES
(6, 1000, 10, 1, NULL, 0, 0),
(7, 1001, 10, 1, NULL, 0, 0),
(8, 1002, 10, 1, NULL, 0, 0),
(9, 1003, 10, 1, NULL, 0, 0),
(10, 1000, 11, 2, NULL, 0, 0),
(11, 1001, 11, 2, NULL, 0, 0),
(12, 1002, 11, 2, NULL, 0, 0),
(13, 1003, 11, 2, NULL, 0, 0),
(14, 1000, 12, 1, NULL, 0, 0),
(15, 1001, 12, 1, NULL, 0, 0),
(16, 1002, 12, 1, NULL, 0, 0),
(17, 1003, 12, 1, NULL, 0, 0),
(21, 1004, 14, 2, '2020-02-19 20:17:14', 2, 7),
(22, 1006, 14, 2, NULL, 0, 0),
(23, 1007, 14, 2, NULL, 0, 0),
(24, 1004, 16, 1, '2020-02-19 19:57:54', 2, 20),
(25, 1006, 16, 1, NULL, 0, 0),
(26, 1007, 16, 1, NULL, 0, 0),
(27, 1004, 20, 1, '2020-02-21 19:41:32', 1, 0),
(28, 1006, 20, 1, NULL, 0, 0),
(29, 1007, 20, 1, NULL, 0, 0),
(30, 1004, 21, 2, '2020-02-21 19:44:09', 1, 0),
(31, 1006, 21, 2, NULL, 0, 0),
(32, 1007, 21, 2, NULL, 0, 0),
(33, 1004, 22, 1, NULL, 0, 0),
(34, 1006, 22, 1, '2020-03-02 16:17:43', 2, 51),
(35, 1007, 22, 1, NULL, 0, 0),
(36, 1004, 23, 2, NULL, 0, 0),
(37, 1006, 23, 2, '2020-03-02 16:30:16', 2, 65),
(38, 1007, 23, 2, NULL, 0, 0),
(42, 1004, 25, 1, '2020-05-19 15:20:23', 2, 5),
(43, 1006, 25, 1, NULL, 0, 0),
(44, 1007, 25, 1, NULL, 0, 0),
(45, 1004, 26, 1, '2020-05-19 15:26:53', 2, 5),
(46, 1006, 26, 1, NULL, 0, 0),
(47, 1007, 26, 1, NULL, 0, 0),
(48, 1004, 27, 1, NULL, 0, 0),
(49, 1006, 27, 1, NULL, 0, 0),
(50, 1007, 27, 1, NULL, 0, 0),
(51, 1004, 28, 1, NULL, 0, 0),
(52, 1006, 28, 1, NULL, 0, 0),
(53, 1007, 28, 1, NULL, 0, 0),
(54, 1004, 29, 1, '2020-05-19 15:42:58', 2, 10),
(55, 1006, 29, 1, NULL, 0, 0),
(56, 1007, 29, 1, NULL, 0, 0);

-- --------------------------------------------------------

--
-- 表的结构 `exam_single_group`
--

CREATE TABLE `exam_single_group` (
  `id` int(10) NOT NULL,
  `single_select_id` int(10) NOT NULL,
  `paper_id` int(10) NOT NULL,
  `paper_type` int(3) NOT NULL,
  `score` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `exam_single_group`
--

INSERT INTO `exam_single_group` (`id`, `single_select_id`, `paper_id`, `paper_type`, `score`) VALUES
(4, 2, 10, 1, 5),
(5, 5, 10, 1, 5),
(6, 6, 10, 1, 5),
(7, 7, 10, 1, 5),
(8, 8, 10, 1, 5),
(9, 3, 11, 2, 5),
(10, 4, 11, 2, 5),
(11, 5, 11, 2, 5),
(12, 8, 11, 2, 5),
(13, 2, 12, 1, 5),
(14, 5, 12, 1, 5),
(15, 8, 12, 1, 5),
(21, 1, 14, 2, 2),
(22, 2, 14, 2, 2),
(23, 4, 14, 2, 2),
(24, 5, 14, 2, 2),
(25, 8, 14, 2, 2),
(26, 1, 16, 1, 3),
(27, 2, 16, 1, 3),
(28, 4, 16, 1, 3),
(29, 5, 16, 1, 3),
(30, 7, 16, 1, 3),
(31, 4, 20, 1, 5),
(32, 6, 20, 1, 5),
(33, 1, 21, 2, 5),
(34, 3, 21, 2, 5),
(35, 5, 21, 2, 5),
(36, 6, 21, 2, 5),
(37, 1, 22, 1, 5),
(38, 4, 22, 1, 5),
(39, 7, 22, 1, 5),
(40, 2, 23, 2, 5),
(41, 3, 23, 2, 5),
(42, 6, 23, 2, 5),
(43, 7, 23, 2, 5),
(44, 8, 23, 2, 5),
(50, 1, 25, 1, 5),
(51, 2, 25, 1, 5),
(52, 5, 25, 1, 5),
(53, 6, 25, 1, 5),
(54, 7, 25, 1, 5),
(55, 2, 26, 1, 5),
(56, 3, 26, 1, 5),
(57, 5, 26, 1, 5),
(58, 7, 26, 1, 5),
(59, 8, 26, 1, 5),
(60, 1, 27, 1, 5),
(61, 3, 27, 1, 5),
(62, 4, 27, 1, 5),
(63, 6, 27, 1, 5),
(64, 8, 27, 1, 5),
(65, 1, 28, 1, 5),
(66, 2, 28, 1, 5),
(67, 3, 28, 1, 5),
(68, 5, 28, 1, 5),
(69, 7, 28, 1, 5),
(70, 1, 29, 1, 5),
(71, 3, 29, 1, 5),
(72, 5, 29, 1, 5),
(73, 6, 29, 1, 5),
(74, 7, 29, 1, 5);

-- --------------------------------------------------------

--
-- 表的结构 `exercise_judgement`
--

CREATE TABLE `exercise_judgement` (
  `id` int(11) NOT NULL,
  `exercise_id` int(14) NOT NULL,
  `test_input` text NOT NULL,
  `test_output` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `exercise_judgement`
--

INSERT INTO `exercise_judgement` (`id`, `exercise_id`, `test_input`, `test_output`) VALUES
(1, 1, '1 6', '7'),
(2, 1, '1 3', '4'),
(3, 2, '1 4', '4'),
(4, 2, '2 4', '8'),
(5, 3, '3 1', '2'),
(6, 3, '6 2', '4'),
(7, 4, '1 3 5', '-1'),
(8, 4, '2 4 6', '0'),
(9, 5, '', 'Hello Java');

-- --------------------------------------------------------

--
-- 表的结构 `exercise_question`
--

CREATE TABLE `exercise_question` (
  `id` int(11) NOT NULL,
  `course_id` int(14) NOT NULL,
  `chapter` varchar(10) NOT NULL,
  `degree` int(2) NOT NULL,
  `title` varchar(30) NOT NULL,
  `exercise_text` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `exercise_question`
--

INSERT INTO `exercise_question` (`id`, `course_id`, `chapter`, `degree`, `title`, `exercise_text`) VALUES
(1, 101, '1', 2, 'a+b', '求a+b'),
(2, 101, '1', 1, 'a*b', '求a*b'),
(3, 101, '1', 1, 'a-b', '求a-b'),
(4, 101, '1', 1, 'a+b-c', '求a+b-c'),
(5, 101, '1', 1, '输出练习', '请输出Hello Java');

-- --------------------------------------------------------

--
-- 表的结构 `exercise_submit`
--

CREATE TABLE `exercise_submit` (
  `id` int(10) NOT NULL,
  `paper_id` int(10) DEFAULT NULL,
  `input` text NOT NULL,
  `time` datetime NOT NULL,
  `type` int(2) NOT NULL,
  `exercise_id` int(14) NOT NULL,
  `student_id` int(14) NOT NULL,
  `state` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `exercise_submit`
--

INSERT INTO `exercise_submit` (`id`, `paper_id`, `input`, `time`, `type`, `exercise_id`, `student_id`, `state`) VALUES
(1, NULL, 'import java.util.*;\npublic class Main{\n	public Integer Solution(Integer a,Integer b){\n		return a+b;\n	}\n\n}', '2020-02-19 19:38:10', 1, 1, 1004, 1),
(2, NULL, 'import java.util.*;\npublic class Main{\n	public Integer Solution(Integer a,Integer b){\n		return a+b;\n	}\n\n}', '2020-02-19 19:38:13', 1, 2, 1004, 0),
(3, NULL, 'import java.util.*;\npublic class Main{\n	public Integer Solution(Integer a,Integer b,Integer c){\n		return a+b-c;\n	}\n\n}', '2020-02-19 20:17:32', 2, 4, 1004, 0),
(4, NULL, 'import java.util.*;\npublic class Main{\n\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n		System.out.println(a*b);\n	}\n}', '2020-02-21 19:41:09', 0, 2, 1004, 1),
(5, NULL, 'import java.util.*;\npublic class Main{\n\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n                int c = sc.nextInt();\n		System.out.println(a+b-c);\n	}\n}  ', '2020-02-21 19:41:55', 1, 4, 1004, 1),
(6, NULL, 'import java.util.*;\npublic class Main{\n\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n		System.out.println(a-b);\n	}\n}', '2020-02-21 19:44:21', 2, 3, 1004, 1),
(7, 22, 'dawdaw', '2020-03-02 16:08:54', 1, 2, 1006, 0),
(8, 22, 'awdawd', '2020-03-02 16:10:27', 1, 2, 1006, 0),
(9, 22, 'awdawd', '2020-03-02 16:11:52', 1, 3, 1006, 0),
(10, 22, 'dwadaw', '2020-03-02 16:11:56', 1, 2, 1006, 0),
(11, 22, 'dawdaw', '2020-03-02 16:15:10', 1, 2, 1006, 0),
(12, 22, 'import java.util.*;\npublic class Main{\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n		System.out.println(a*b);\n	}\n}', '2020-03-02 16:15:38', 1, 2, 1006, 1),
(13, 22, 'import java.util.*;\npublic class Main{\n\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n		System.out.println(a-b);\n	}\n}', '2020-03-02 16:15:55', 1, 3, 1006, 1),
(14, 23, 'dwadaw', '2020-03-02 16:30:27', 2, 1, 1006, 0),
(15, 23, 'dwadaw', '2020-03-02 16:30:31', 2, 2, 1006, 0),
(16, 23, 'import java.util.*;\npublic class Main{\n\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n		System.out.println(a+b);\n	}\n}', '2020-03-02 16:30:37', 2, 1, 1006, 1),
(17, 23, 'import java.util.*;\npublic class Main{\n\n	public static void main(String[] args){\n		Scanner sc = new Scanner(System.in);\n		int a = sc.nextInt();\n		int b = sc.nextInt();\n		System.out.println(a*b);\n	}\n}', '2020-03-02 16:30:49', 2, 2, 1006, 1),
(18, NULL, 'public class HelloWorld{\n    public static void main(String[] args) {\n		System.out.println(\"Hello Java\");\n	}\n}', '2020-04-17 11:32:50', 0, 5, 1004, 0),
(19, NULL, 'public class Main{\n    public static void main(String[] args) {\n		System.out.println(\"Hello Java\");\n	}\n}', '2020-04-17 11:32:59', 0, 5, 1004, 1),
(20, NULL, 'public class Main{\n\n	public static void main(String[] args){\n		System.out.println(\"Hello Java\");\n	}\n}\n\n', '2020-05-19 15:01:15', 0, 5, 1004, 1),
(21, NULL, 'public class Main{\n\n	public static void main(String[] args){\n		System.out.println(\"Hell Java\");\n	}\n}\n\n', '2020-05-19 15:01:22', 0, 5, 1004, 0),
(22, NULL, 'public class Main{\n\n	public static void main(String[] args){\n		System.out.println\n	}\n}\n\n', '2020-05-19 15:01:29', 0, 5, 1004, 0);

-- --------------------------------------------------------

--
-- 表的结构 `fill_blank_question`
--

CREATE TABLE `fill_blank_question` (
  `id` int(11) NOT NULL,
  `course_id` int(14) NOT NULL,
  `chapter` varchar(10) NOT NULL,
  `section` varchar(10) NOT NULL,
  `question_text` text NOT NULL,
  `degree` int(2) NOT NULL,
  `answer` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `fill_blank_question`
--

INSERT INTO `fill_blank_question` (`id`, `course_id`, `chapter`, `section`, `question_text`, `degree`, `answer`) VALUES
(1, 101, '1', '1', '1+2=_?', 1, '3'),
(2, 101, '1', '1', '1+3=_?', 3, '4'),
(3, 102, '1', '1', '1+5=_?', 2, '6'),
(4, 101, '1', '1', 'Spring中aop通过_____________配置只读事务', 1, 'read-only=true'),
(5, 101, '1', '1', '整合spring中我们一般将事务控制在_______层', 1, 'service'),
(6, 101, '1', '1', 'Spring 中事务的四大特性：原子性,____,隔离性,持久性', 1, '一致性'),
(7, 101, '1', '1', 'springMVC前端控制器是_________________', 1, 'DispatcherServlet');

-- --------------------------------------------------------

--
-- 表的结构 `notice`
--

CREATE TABLE `notice` (
  `id` int(10) NOT NULL,
  `title` text NOT NULL,
  `content` text NOT NULL,
  `create_time` datetime NOT NULL,
  `oper_name` varchar(18) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `notice`
--

INSERT INTO `notice` (`id`, `title`, `content`, `create_time`, `oper_name`) VALUES
(1, '平台升级V1.0', '关于平台V1.0版本升级的通知....', '2020-02-03 10:00:00', '王强'),
(2, '平台升级V1.2', '关于平台V1.2升级的通知....', '2020-02-05 16:00:00', '王强'),
(3, '平台升级V1.5', '关于平台V1.5升级的通知....', '2020-02-19 20:00:00', '王强');

-- --------------------------------------------------------

--
-- 表的结构 `single_select_question`
--

CREATE TABLE `single_select_question` (
  `id` int(11) NOT NULL,
  `course_id` int(14) NOT NULL,
  `chapter` varchar(10) NOT NULL,
  `section` varchar(10) NOT NULL,
  `question_text` text NOT NULL,
  `degree` int(2) NOT NULL,
  `optionA` varchar(200) NOT NULL,
  `optionB` varchar(200) NOT NULL,
  `optionC` varchar(200) NOT NULL,
  `optionD` varchar(200) NOT NULL,
  `answer` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `single_select_question`
--

INSERT INTO `single_select_question` (`id`, `course_id`, `chapter`, `section`, `question_text`, `degree`, `optionA`, `optionB`, `optionC`, `optionD`, `answer`) VALUES
(1, 101, '1', '1', '1+1=_?', 1, '2', '3', '4', '5', 'A'),
(2, 101, '1', '1', '1+2=_?', 2, '2', '3', '4', '5', 'B'),
(3, 101, '1', '1', 'java程序中，main方法的格式正确的是（）', 1, 'static void main（String[] args）', 'public void main（String[] args）', 'public static void main（String[]s）', 'public static void main（String[] args）', 'D'),
(4, 101, '1', '1', '在java中，（）对象可以使用键/值的形式保存数据。', 1, 'ArrayList', 'HashSet', 'HashMap ', 'LinkedList', 'C'),
(5, 101, '1', '1', '关于sleep()和wait()，以下描述错误的一项是（ ）', 1, 'sleep是线程类（Thread）的方法，wait是Object类的方法；', 'sleep不释放对象锁，wait放弃对象锁；', 'sleep暂停线程、但监控状态仍然保持，结束后会自动恢复；', 'wait后进入等待锁定池，只有针对此对象发出notify方法后获得对象锁进入运行状态。', 'D'),
(6, 101, '1', '1', '提供Java存取数据库能力的包是（）', 1, 'java.sql', 'java.awt', 'java.lang', 'java.swing', 'A'),
(7, 101, '1', '1', '方法resume()负责恢复哪些线程的执行（ ）', 1, '通过调用stop()方法而停止的线程。', '通过调用sleep()方法而停止的线程。', '通过调用wait()方法而停止的线程。', '通过调用suspend()方法而停止的线程。', 'D'),
(8, 101, '1', '1', 'Java I/O程序设计中，下列描述正确的是', 1, 'OutputStream用于写操作', 'InputStream用于写操作', 'I/O库不支持对文件可读可写API', 'OutputStream用于读操作', 'A');

-- --------------------------------------------------------

--
-- 表的结构 `user_admins`
--

CREATE TABLE `user_admins` (
  `id` int(10) NOT NULL,
  `admin_id` int(14) NOT NULL,
  `password` varchar(18) NOT NULL,
  `level` int(1) NOT NULL DEFAULT '1',
  `name` varchar(18) NOT NULL,
  `email` varchar(32) NOT NULL,
  `phone_num` char(11) NOT NULL,
  `id_card` char(19) NOT NULL,
  `status` int(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `user_admins`
--

INSERT INTO `user_admins` (`id`, `admin_id`, `password`, `level`, `name`, `email`, `phone_num`, `id_card`, `status`) VALUES
(1, 100, '123', 1, '王强', '982052440@zz.com', '12345678913', '510602199711076837', 0),
(2, 101, '123', 1, '黑客', '213123124@qq.com', '12345676911', '510632199711076837', 0),
(3, 102, '333', 1, '黑客1', '213143124@qq.com', '12345876911', '510632195711076837', 0),
(4, 103, '123', 1, '黑客2', '2131263124@qq.com', '12345672911', '510632199011076837', 0);

-- --------------------------------------------------------

--
-- 表的结构 `user_students`
--

CREATE TABLE `user_students` (
  `id` int(10) NOT NULL,
  `student_id` int(14) NOT NULL,
  `password` varchar(18) NOT NULL,
  `name` varchar(18) NOT NULL,
  `class_id` int(10) NOT NULL DEFAULT '0',
  `email` varchar(32) NOT NULL,
  `phone_num` char(11) NOT NULL,
  `id_card` char(19) NOT NULL,
  `points` int(10) NOT NULL DEFAULT '0',
  `status` int(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `user_students`
--

INSERT INTO `user_students` (`id`, `student_id`, `password`, `name`, `class_id`, `email`, `phone_num`, `id_card`, `points`, `status`) VALUES
(1, 1000, '123', '肖明', 10, '983052490@qq.com', '13541201622', '510602199711076827', 0, 0),
(2, 1001, '123', '西蓝花', 10, '921383@qq.com', '12345678911', '510602199711076811', 0, 0),
(3, 1002, '123', '番茄', 10, '9213831@qq.com', '12345678909', '510602199711076123', 0, 0),
(4, 1003, '123', '土豆', 10, ' 9213831@qq.com', ' 1234567890', ' 510602199711076021', 0, 0),
(5, 1004, '123456', '肥牛', 12, '98213@qq.com', '12345678901', ' 510602199711076201', 20, 0),
(6, 1005, '123', '牛排', 13, '12345678989@qq.com', '12345678989', '510602199711076711', 0, 0),
(7, 1006, '123', '葡萄', 12, '12345673989@qq.com', '12345678389', '510402199711076711', 10, 0),
(8, 1007, '123', '橘子', 12, '12345672989@qq.com', '12345678289', '510502199711076711', 0, 0),
(9, 1008, '123', '栗子', 0, '12341678989@qq.com', '12345671189', '510802199711076711', 1000, 0),
(10, 1009, '123', '豌豆尖', 0, '12545678989@qq.com', '12345228989', '510602199711076711', 0, 0),
(11, 1010, '333', '猪肉', 0, '16745678989@qq.com', '12345338989', '510202199711076711', 0, 0);

-- --------------------------------------------------------

--
-- 表的结构 `user_teachers`
--

CREATE TABLE `user_teachers` (
  `id` int(10) NOT NULL,
  `teacher_id` int(14) NOT NULL,
  `password` varchar(18) NOT NULL,
  `name` varchar(18) NOT NULL,
  `email` varchar(32) NOT NULL,
  `phone_num` char(11) NOT NULL,
  `id_card` char(19) NOT NULL,
  `status` int(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `user_teachers`
--

INSERT INTO `user_teachers` (`id`, `teacher_id`, `password`, `name`, `email`, `phone_num`, `id_card`, `status`) VALUES
(1, 1001, '123', '李四', '982052390@qq.com', '12345678990', '510602199711076821', 0),
(2, 1002, '111111', '王菲', 'dawdaw@cc.com', '12345678943', '510892198822771132', 0),
(3, 1003, '123', '刘备', '4124@qq.com', '12345678955', '510602199711076123', 0),
(4, 1004, '123', '哈哈', '21398@qq.com', '12345678932', '510602197711076821', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `classes`
--
ALTER TABLE `classes`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `code_discuss`
--
ALTER TABLE `code_discuss`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `code_judgement`
--
ALTER TABLE `code_judgement`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `code_question`
--
ALTER TABLE `code_question`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `code_submit`
--
ALTER TABLE `code_submit`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `course_content`
--
ALTER TABLE `course_content`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `discuss_comment`
--
ALTER TABLE `discuss_comment`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `discuss_question`
--
ALTER TABLE `discuss_question`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `exam_answerinfo`
--
ALTER TABLE `exam_answerinfo`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `exam_blank_group`
--
ALTER TABLE `exam_blank_group`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `exam_code_group`
--
ALTER TABLE `exam_code_group`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `exam_paperinfo`
--
ALTER TABLE `exam_paperinfo`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `exam_scoreinfo`
--
ALTER TABLE `exam_scoreinfo`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `exam_single_group`
--
ALTER TABLE `exam_single_group`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `exercise_judgement`
--
ALTER TABLE `exercise_judgement`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `exercise_question`
--
ALTER TABLE `exercise_question`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `exercise_submit`
--
ALTER TABLE `exercise_submit`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `fill_blank_question`
--
ALTER TABLE `fill_blank_question`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `notice`
--
ALTER TABLE `notice`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `single_select_question`
--
ALTER TABLE `single_select_question`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_admins`
--
ALTER TABLE `user_admins`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_students`
--
ALTER TABLE `user_students`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_teachers`
--
ALTER TABLE `user_teachers`
  ADD PRIMARY KEY (`id`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `classes`
--
ALTER TABLE `classes`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- 使用表AUTO_INCREMENT `code_discuss`
--
ALTER TABLE `code_discuss`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- 使用表AUTO_INCREMENT `code_judgement`
--
ALTER TABLE `code_judgement`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- 使用表AUTO_INCREMENT `code_question`
--
ALTER TABLE `code_question`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- 使用表AUTO_INCREMENT `code_submit`
--
ALTER TABLE `code_submit`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- 使用表AUTO_INCREMENT `courses`
--
ALTER TABLE `courses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- 使用表AUTO_INCREMENT `course_content`
--
ALTER TABLE `course_content`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- 使用表AUTO_INCREMENT `discuss_comment`
--
ALTER TABLE `discuss_comment`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- 使用表AUTO_INCREMENT `discuss_question`
--
ALTER TABLE `discuss_question`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- 使用表AUTO_INCREMENT `exam_answerinfo`
--
ALTER TABLE `exam_answerinfo`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=73;

--
-- 使用表AUTO_INCREMENT `exam_blank_group`
--
ALTER TABLE `exam_blank_group`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- 使用表AUTO_INCREMENT `exam_code_group`
--
ALTER TABLE `exam_code_group`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- 使用表AUTO_INCREMENT `exam_paperinfo`
--
ALTER TABLE `exam_paperinfo`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- 使用表AUTO_INCREMENT `exam_scoreinfo`
--
ALTER TABLE `exam_scoreinfo`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- 使用表AUTO_INCREMENT `exam_single_group`
--
ALTER TABLE `exam_single_group`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=75;

--
-- 使用表AUTO_INCREMENT `exercise_judgement`
--
ALTER TABLE `exercise_judgement`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- 使用表AUTO_INCREMENT `exercise_question`
--
ALTER TABLE `exercise_question`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- 使用表AUTO_INCREMENT `exercise_submit`
--
ALTER TABLE `exercise_submit`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- 使用表AUTO_INCREMENT `fill_blank_question`
--
ALTER TABLE `fill_blank_question`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- 使用表AUTO_INCREMENT `notice`
--
ALTER TABLE `notice`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- 使用表AUTO_INCREMENT `single_select_question`
--
ALTER TABLE `single_select_question`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- 使用表AUTO_INCREMENT `user_admins`
--
ALTER TABLE `user_admins`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- 使用表AUTO_INCREMENT `user_students`
--
ALTER TABLE `user_students`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- 使用表AUTO_INCREMENT `user_teachers`
--
ALTER TABLE `user_teachers`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
