/*
SQLyog Ultimate v8.32 
MySQL - 5.6.20 : Database - jeecmsv6_c
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`jeecmsv6_c` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `jeecmsv6_c`;

/*Table structure for table `jc_jautopost` */

DROP TABLE IF EXISTS `jc_jautopost`;

CREATE TABLE `jc_jautopost` (
  `jautopost_id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL,
  `channel_id` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `acq_name` varchar(50) NOT NULL COMMENT '采集名称',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '停止时间',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '当前状态(0:静止;1:采集;2:暂停)',
  `curr_num` int(11) NOT NULL DEFAULT '0' COMMENT '当前号码',
  `curr_item` int(11) NOT NULL DEFAULT '0' COMMENT '当前条数',
  `total_item` int(11) NOT NULL DEFAULT '0' COMMENT '每页总条数',
  `pause_time` int(11) NOT NULL DEFAULT '0' COMMENT '暂停时间(毫秒)',
  `page_encoding` varchar(20) NOT NULL DEFAULT 'UTF-8' COMMENT '页面编码',
  `plan_list` longtext COMMENT '采集列表',
  `dynamic_addr` varchar(255) DEFAULT NULL COMMENT '动态地址',
  `dynamic_start` int(11) DEFAULT NULL COMMENT '页码开始',
  `dynamic_end` int(11) DEFAULT NULL COMMENT '页码结束',
  `linkset_start` varchar(255) DEFAULT NULL COMMENT '内容链接区开始',
  `linkset_end` varchar(255) DEFAULT NULL COMMENT '内容链接区结束',
  `link_start` varchar(255) DEFAULT NULL COMMENT '内容链接开始',
  `link_end` varchar(255) DEFAULT NULL COMMENT '内容链接结束',
  `img_src` varchar(255) DEFAULT 'src' COMMENT '标题开始',
  `auto_charset` char(1) DEFAULT 'N' COMMENT '标题结束',
  `keywords_start` varchar(255) DEFAULT NULL COMMENT '关键字开始',
  `keywords_end` varchar(255) DEFAULT NULL COMMENT '关键字结束',
  `description_start` varchar(255) DEFAULT NULL COMMENT '描述开始',
  `description_end` varchar(255) DEFAULT NULL COMMENT '描述结束',
  `content_start` varchar(255) DEFAULT NULL COMMENT '内容开始',
  `content_end` varchar(255) DEFAULT NULL COMMENT '内容结束',
  `pagination_start` varchar(255) DEFAULT NULL COMMENT '内容分页开始',
  `pagination_end` varchar(255) DEFAULT NULL COMMENT '内容分页结束',
  `queue` int(11) NOT NULL DEFAULT '0' COMMENT '队列',
  `repeat_check_type` varchar(20) NOT NULL DEFAULT 'NONE' COMMENT '重复类型',
  `img_acqu` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否采集图片',
  `content_prefix` varchar(255) DEFAULT NULL COMMENT '内容地址补全url',
  `img_prefix` varchar(255) DEFAULT NULL COMMENT '图片地址补全url',
  `view_start` varchar(255) DEFAULT NULL COMMENT '浏览量开始',
  `view_end` varchar(255) DEFAULT NULL COMMENT '浏览量结束',
  `view_id_start` varchar(255) DEFAULT NULL COMMENT 'id前缀',
  `view_id_end` varchar(255) DEFAULT NULL COMMENT 'id后缀',
  `view_link` varchar(255) DEFAULT NULL COMMENT '浏览量动态访问地址',
  `releaseTime_start` varchar(255) DEFAULT NULL COMMENT '发布时间开始',
  `releaseTime_end` varchar(255) DEFAULT NULL COMMENT '发布时间结束',
  `author_start` varchar(255) DEFAULT NULL COMMENT '作者开始',
  `author_end` varchar(255) DEFAULT NULL COMMENT '作者结束',
  `origin_start` varchar(255) DEFAULT NULL COMMENT '来源开始',
  `origin_end` varchar(255) DEFAULT NULL COMMENT '来源结束',
  `releaseTime_format` varchar(255) DEFAULT NULL COMMENT '发布时间格式',
  `list_selector` varchar(255) DEFAULT NULL COMMENT '文章列表抓取方式',
  `list_by_url` varchar(255) DEFAULT NULL COMMENT '文章列表按URL抓取方式',
  `list_by_css` varchar(255) DEFAULT NULL COMMENT '文章列表按CSS抓取方式',
  `title_selector` varchar(255) DEFAULT NULL COMMENT '文章标题抓取方式',
  `title_by_tag_begin` varchar(255) DEFAULT NULL COMMENT '文章标题抓取起始无重复HTML',
  `title_by_tag_end` varchar(255) DEFAULT NULL COMMENT '文章标题抓取结束无重复HTML',
  `title_by_css` varchar(255) DEFAULT NULL COMMENT '文章标题CSS选择器',
  `content_selector` varchar(255) DEFAULT NULL COMMENT '文章内容抓取方式',
  `content_by_tag_begin` varchar(255) DEFAULT NULL COMMENT '文章内容抓取起始无重复HTML',
  `content_by_tag_end` varchar(255) DEFAULT NULL COMMENT '文章内容抓取结束无重复HTML',
  `content_by_css` varchar(255) DEFAULT NULL COMMENT '文章内容抓取CSS选择器',
  `remove_content_link` char(1) DEFAULT 'N' COMMENT '文章内容过滤方式',
  `remove_content_by_tag_begin` varchar(2000) DEFAULT NULL COMMENT '文章内容过滤方式起始HTML',
  `remove_content_by_tag_end` varchar(2000) DEFAULT NULL COMMENT '文章内容过滤方式结束HTML',
  `remove_content_by_css` varchar(2000) DEFAULT NULL COMMENT '文章内容过滤方式CSS选择器',
  PRIMARY KEY (`jautopost_id`),
  KEY `fk_jc_acquisition_channel` (`channel_id`),
  KEY `fk_jc_acquisition_contenttype` (`type_id`),
  KEY `fk_jc_acquisition_site` (`site_id`),
  KEY `fk_jc_acquisition_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8 COMMENT='CMS自动采集表';

/*Data for the table `jc_jautopost` */

LOCK TABLES `jc_jautopost` WRITE;

insert  into `jc_jautopost`(`jautopost_id`,`site_id`,`channel_id`,`type_id`,`user_id`,`acq_name`,`start_time`,`end_time`,`status`,`curr_num`,`curr_item`,`total_item`,`pause_time`,`page_encoding`,`plan_list`,`dynamic_addr`,`dynamic_start`,`dynamic_end`,`linkset_start`,`linkset_end`,`link_start`,`link_end`,`img_src`,`auto_charset`,`keywords_start`,`keywords_end`,`description_start`,`description_end`,`content_start`,`content_end`,`pagination_start`,`pagination_end`,`queue`,`repeat_check_type`,`img_acqu`,`content_prefix`,`img_prefix`,`view_start`,`view_end`,`view_id_start`,`view_id_end`,`view_link`,`releaseTime_start`,`releaseTime_end`,`author_start`,`author_end`,`origin_start`,`origin_end`,`releaseTime_format`,`list_selector`,`list_by_url`,`list_by_css`,`title_selector`,`title_by_tag_begin`,`title_by_tag_end`,`title_by_css`,`content_selector`,`content_by_tag_begin`,`content_by_tag_end`,`content_by_css`,`remove_content_link`,`remove_content_by_tag_begin`,`remove_content_by_tag_end`,`remove_content_by_css`) values (14,1,79,1,1,'凤凰军事','2015-01-18 21:50:14','2015-01-18 21:53:36',0,0,0,0,500,'UTF-8','http://news.ifeng.com/mil/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','.col_l .box400 .list01 a','TITLE_CSS','','','#artical_topic','CONTENT_CSS','','','#main_content','Y','<p><strong>本文系凤凰军事','</strong></p>','#embed_hzh_div︾.ifengLogo'),(15,1,79,1,1,'国际军情','2015-01-18 21:48:01','2015-01-18 21:50:13',0,0,0,0,500,'UTF-8','http://military.china.com/news2/index.html','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','#newsList a','TITLE_CSS','','','#chan_newsTitle','CONTENT_CSS','','','#chan_newsDetail','Y','','','.artiLogo︾.adclass︾.editor'),(16,1,11,1,1,'网易新闻-娱乐','2015-01-18 21:46:49','2015-01-18 21:47:49',0,0,0,0,10,'GBK','http://ent.163.com/star/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','div.item-Text h2 a','TITLE_CSS','','','#h1title','CONTENT_CSS','','','#endText','Y','','','.ep-source︾.cDGray︾.gg200x300︾.left'),(17,1,80,1,1,'新浪新闻-汽车','2015-01-18 21:46:09','2015-01-18 21:46:48',0,0,0,0,10,'gb2312','http://auto.sina.com.cn/news/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','h5 a','TITLE_CSS','','','#artibodyTitle','CONTENT_CSS','','','#articleContent','Y','','','.news_like︾.otherContent_01'),(18,1,81,1,1,'中国新闻-房产','2015-01-18 21:45:02','2015-01-18 21:46:10',0,0,0,0,10,'gb2312','http://www.chinanews.com/house/gd.shtml','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','.content_list  .dd_bt a','TITLE_CSS','','','.content h1','CONTENT_CSS','','','.left_zw','Y','<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"padding-right:10px;\">','</table>','#function_code_page'),(19,1,70,1,1,'全球视角-科技','2015-01-18 21:44:23','2015-01-18 21:45:04',0,0,0,0,10,'UTF-8','http://tech.huanqiu.com/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','.conFour a','TITLE_CSS','','','div.conText h1','CONTENT_CSS','','','.text','Y','','','.ad250x250︾.fLeft︾.marRig10︾.reTopics︾.editorSign'),(20,1,70,1,1,'福布斯-新能源','2015-01-18 21:43:32','2015-01-18 21:43:54',0,0,0,0,10,'UTF-8','http://www.forbeschina.com/tech/energy/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','.newslist dt a','TITLE_CSS','','','#article_title','CONTENT_CSS','','','.articlecon','Y','<p> <em>本文系','</em> </p>',''),(21,1,70,1,1,'新浪-科技前沿','2015-01-18 21:42:58','2015-01-18 21:43:31',0,0,0,0,500,'UTF-8','http://tech.sina.com.cn/discovery/invention/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','.news-item, .first-news-item h2 a, .img-news-item h2 a','TITLE_CSS','','','.swp-tit h2, .blkContainerSblk h1, .swp-tit h2','CONTENT_CSS','','','.BSHARE_POP, .blkContainerSblkCon, .blkContainerSblkCon_14','Y','','','.otherContent_01'),(22,1,77,1,1,'砍柴网-创业','2015-01-18 21:38:58','2015-01-18 21:39:14',0,0,0,0,500,'UTF-8','http://www.ikanchai.com/start/work/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','.list2 h3 a, .listl  h3 a','TITLE_CSS','','','.listltitle h3','CONTENT_CSS','','','.article-content','Y','','',''),(23,1,70,1,1,'砍柴网-精选','2015-01-18 21:37:54','2015-01-18 21:38:27',0,0,0,0,500,'UTF-8','http://www.ikanchai.com/hardware/choice/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','.list2 h3 a','TITLE_CSS','','','.listltitle h3','CONTENT_CSS','','','.article-content','N','','',''),(24,1,73,1,1,'极客范-Android','2015-01-18 21:53:38','2015-01-18 21:54:08',0,0,0,0,500,'UTF-8','http://www.geekfan.net/category/android-2/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','.post-text h2 a','TITLE_CSS','','','h1.page-title','CONTENT_CSS','','','#post-content','Y','<script async','</script>','.jiathis_style_24x24︾.adsbygoogle'),(25,1,74,1,1,'极客范-ios','2015-01-18 21:13:23','2015-01-18 21:14:07',0,0,0,0,500,'UTF-8','http://www.geekfan.net/category/ios/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','.post-text h2 a','TITLE_CSS','','','h1.page-title','CONTENT_CSS','','','#post-content','Y','<script async','</script>','.jiathis_style_24x24︾.adsbygoogle'),(26,1,74,1,1,'极客范-Mac','2015-01-18 21:10:47','2015-01-18 21:13:18',0,0,0,0,0,'UTF-8','http://www.geekfan.net/category/mac/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','.post-text h2 a','TITLE_CSS','','','h1.page-title','CONTENT_CSS','','','#post-content','Y','<script async','</script>','.jiathis_style_24x24︾.adsbygoogle'),(28,1,76,1,1,'网易-移动互联网','2015-01-18 21:08:27','2015-01-18 21:10:47',0,0,0,0,500,'GBK','http://tech.163.com/internet','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','h3.bigsize a','TITLE_CSS','','','#h1title','CONTENT_CSS','','','#endText','Y','','','.gg200x300︾.ep-source︾.cDGray'),(29,1,74,1,1,'网易-苹果','2015-01-18 21:07:10','2015-01-18 21:07:58',0,0,0,0,500,'gb2312','http://digi.163.com/apple/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','.titleBar h1 a','TITLE_CSS','','','#h1title','CONTENT_CSS','','','#endText','N','','','.gg200x300︾.ep-source︾.cDGray'),(30,1,71,1,1,'分类浏览: 相机','2015-01-18 21:04:27','2015-01-18 21:06:23',0,0,0,0,500,'UTF-8','http://cn.engadget.com/topics/cameras/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','article.post-mini a','TITLE_CSS','','','h1.h1','CONTENT_CSS','','','.post-body','N','','','.read-more︾.post-gallery'),(31,1,71,1,1,'分类浏览: 手机','2015-01-18 20:59:26','2015-01-18 21:04:31',0,0,0,0,500,'UTF-8','http://cn.engadget.com/topics/mobilephones/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','article.post-mini a','TITLE_CSS','','','h1.h1','CONTENT_CSS','','','.post-body','Y','','','.read-more︾.post-gallery'),(32,1,76,1,1,'分类浏览: 互联网','2015-01-18 20:57:29','2015-01-18 21:01:32',0,0,0,0,500,'UTF-8','http://cn.engadget.com/topics/internet/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','article.post-mini a','TITLE_CSS','','','h1.h1','CONTENT_CSS','','','.post-body','Y','','','.read-more︾.post-gallery'),(33,1,71,1,1,'分类浏览: 膝上电脑','2015-01-18 20:55:30','2015-01-18 20:58:33',0,0,0,0,500,'UTF-8','http://cn.engadget.com/topics/laptops/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','article.post-mini a','TITLE_CSS','','','h1.h1','CONTENT_CSS','','','.post-body','Y','','','.read-more︾.post-gallery'),(34,1,71,1,1,'分类浏览: 存储','2015-01-18 20:52:11','2015-01-18 20:55:26',0,0,0,0,500,'UTF-8','http://cn.engadget.com/topics/storage/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','article.post-mini a','TITLE_CSS','','','h1.h1','CONTENT_CSS','','','.post-body','Y','','','.read-more︾.post-gallery'),(35,1,78,1,1,'分类浏览: 软件应用','2015-01-18 20:51:09','2015-01-18 20:51:23',0,0,0,0,500,'UTF-8','http://cn.engadget.com/topics/software-apps/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','article.post-mini a','TITLE_CSS','','','h1.h1','CONTENT_CSS','','','.post-body','Y','','','.read-more︾.post-gallery'),(36,1,72,1,1,'IT-新浪博客','2015-01-17 18:28:35','2015-01-17 18:29:37',0,0,0,0,10000,'UTF-8','http://blog.sina.com.cn/lm/tech/','',2,10,NULL,NULL,NULL,NULL,'real_src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','.bc ul li a','TITLE_CSS','','','.atcbox h1','CONTENT_CSS','','','.articalContent','Y','<font>我的更多文章：</font>','</ul></div>','.into_bloger︾.shareUp'),(37,1,74,1,1,'苹果汇_新浪科技_新浪网','2015-01-18 20:47:56','2015-01-18 20:51:20',0,0,0,0,5000,'UTF-8','http://tech.sina.com.cn/apple/','',2,10,NULL,NULL,NULL,NULL,'src','Y',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','div.img-news-item h2 a','TITLE_CSS','','','#artibodyTitle','CONTENT_CSS','','','#artibody','Y','','',''),(38,1,76,1,1,'互联网|中国概念股_新浪科技_新浪网','2015-01-18 20:40:37','2015-01-18 20:42:00',0,0,0,0,500,'UTF-8','http://tech.sina.com.cn/internet/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','.ul02 h3 a','TITLE_CSS','','','#artibodyTitle','CONTENT_CSS','','','#artibody','Y','','','.otherContent_01'),(40,1,80,1,1,'驱动之家-汽车','2015-01-18 20:39:49','2015-01-18 20:40:21',0,0,0,0,500,'GBK','http://news.mydrivers.com/class/807/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','div.title span > a','TITLE_CSS','','','.news_bt','CONTENT_CSS','','','.news_info','Y','<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">','</table>','.news_bq'),(41,1,71,1,1,'驱动之家-家电数码','2015-01-18 20:38:29','2015-01-18 20:39:14',0,0,0,0,500,'GBK','http://news.mydrivers.com/class/804/','',NULL,NULL,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','div.title span > a','TITLE_CSS','','','.news_bt','CONTENT_CSS','','','.news_info','Y','<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">','</table>','.news_bq'),(42,1,72,1,1,'程序师 ','2015-01-18 20:36:30','2015-01-18 20:38:24',0,0,0,0,500,'UTF-8','http://www.techug.com/','',2,10,NULL,NULL,NULL,NULL,'data-original','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','h1.recent-title a','TITLE_CSS','','','h1.entry-title','CONTENT_CSS','','','.entry-content','N','<div><script︾<h2>请关注我︾<iframe︾<script async︾<script type=\"text/javascript\"','</script></div>︾</h2>︾</iframe>︾</script>︾</script>','.sd-title︾.c︾.square_shares︾#social-actions︾.adsbygoogle'),(43,1,78,1,1,'少数派-应用','2015-01-18 20:17:21','2015-01-18 20:20:19',0,0,0,0,1200,'UTF-8','http://sspai.com/category/apps','',2,10,NULL,NULL,NULL,NULL,'data-original','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','h2.title a','TITLE_CSS','','','.misc h1.title','CONTENT_CSS','','','.content','N','<p><span style=\"color: #808080;\">','</span></p>',''),(44,1,78,1,1,'爱范儿-应用','2015-01-18 20:05:28','2015-01-18 20:27:57',0,0,0,0,1300,'UTF-8','http://www.ifanr.com/category/special/app-special','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','h2.entry-name a','TITLE_CSS','','','.entry-header h1.entry-name a','CONTENT_CSS','','','#entry-content','N','<p>题图来','</p>',''),(45,1,80,1,1,'爱范儿-汽车','2015-01-18 19:51:34','2015-01-18 20:04:51',0,0,0,0,1500,'UTF-8','http://www.ifanr.com/category/special/intelligentcar','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','h2.entry-name a','TITLE_CSS','','','.entry-header h1.entry-name a','CONTENT_CSS','','','#entry-content','N','<p>题图来','</p>',''),(46,1,76,1,1,'虎嗅-看点','2015-01-18 19:42:33','2015-01-18 19:43:13',0,0,0,0,2500,'UTF-8','http://www.huxiu.com/focus','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','.ul-list li a[href*=article/]','TITLE_CSS','','','.neirong h1','CONTENT_CSS','','','.neirong-box','N','<p><span label=\"备注\"','</span></p>','.neirong-shouquan︾.askAuthor︾.add-collect︾.read-later︾.toggle-tooltip'),(47,1,77,1,1,'创业邦 > 起步 > 创业ABC > 文章列表','2015-01-18 19:39:22','2015-01-18 19:40:24',0,0,0,0,500,'UTF-8','http://www.cyzone.cn/category/18/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','ul.list a','TITLE_CSS','','','.article_title h1','CONTENT_CSS','','','#text','N','<p>（via','</p>',''),(49,1,76,1,1,'PingWest-互联网','2015-01-18 19:32:12','2015-01-18 19:32:53',0,0,0,0,2500,'gb2312','http://www.pingwest.com/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','.news-list, h2 a .index-news , .item-con h2 a','TITLE_CSS','','','.post-head h1','CONTENT_CSS','','','#sc-container','N','','',''),(50,1,70,1,1,'科技_科技创新_最新科技趋势_福布斯中文网<','2015-01-18 19:30:17','2015-01-18 19:31:51',0,0,0,0,4500,'UTF-8','http://www.forbeschina.com/tech/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','dt a','TITLE_CSS','','','p.articletitle2, p.articletitle','CONTENT_CSS','','','.articlecon','N','','',''),(51,1,81,1,1,'房地产_房地产公司排名_福布斯中文网','2015-01-18 19:29:20','2015-01-18 19:29:52',0,0,0,0,1500,'UTF-8','http://www.forbeschina.com/business/house/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','a.buss_left_left_title','TITLE_CSS','','','p.articletitle, p.articletitle2, p.articletitle2,#article_title','CONTENT_CSS','','','.articlecon,.detail','N','','',''),(52,1,82,1,1,'煎蛋：地球上没有新鲜事','2015-01-18 19:16:15','2015-01-18 19:22:11',0,0,0,0,500,'UTF-8','http://jandan.net/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','h2 a','TITLE_CSS','','','.post h1 a, .f h1 a','CONTENT_CSS','','','.post:first','N','<script>︾<h3 style=\"font-size:11px;font-weight:normal;\">','</script>︾</h3>','.time_s︾.comment-big︾h > a︾.share-links︾.wp-zan'),(53,1,73,1,1,'android','2015-01-18 18:57:30','2015-01-18 18:58:27',0,0,0,0,500,'UTF-8','http://news.hiapk.com/android/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','dt a','TITLE_CSS','','','h1.article_title','CONTENT_CSS','','','.article_con','N','','',''),(54,1,83,1,1,'Engadget Reviews','2015-01-18 18:43:58','2015-01-18 18:46:39',0,0,0,0,2500,'UTF-8','http://www.engadget.com/reviews','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','#reviews-list li > a','TITLE_CSS','','','h1.h1','CONTENT_CSS','','','.post-body','N','','','.post-gallery︾.post-meta︾#gdgt-wrapper︾.rail-ad, .rail-ad-topper, .fb-view'),(55,1,83,1,1,'Technically Speaking','2015-01-18 18:38:00','2015-01-18 18:41:32',0,0,0,0,2500,'UTF-8','','http://blog.advaoptical.com/?page=[page]',1,5,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','.post-listing a.read-more','TITLE_CSS','','','.title-container h3','CONTENT_CSS','','','.post-content','N','','',''),(56,1,83,1,1,'NFC World','2015-01-18 18:35:22','2015-01-18 18:36:38',0,0,0,0,3500,'UTF-8','http://www.nfcworld.com/','',2,10,NULL,NULL,NULL,NULL,'src','N',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','p.more a','TITLE_CSS','','','h1.entry-title','CONTENT_CSS','','','.entry-content','N','','',''),(57,1,83,1,1,'symbian-news','2015-01-18 18:25:58','2015-01-18 18:29:46',0,0,0,0,3500,'UTF-8','http://symbian-developers.net/category/symbian-news/','',2,10,NULL,NULL,NULL,NULL,'src','Y',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','.entry-title a','TITLE_CSS','','','h1.entry-title','CONTENT_CSS','','','.entry-content','N','','','.sharedaddy, .sd-sharing-enabled'),(58,1,83,1,1,'pinkbike','2015-01-18 20:30:07','2015-01-18 20:34:58',0,0,0,0,500,'UTF-8','http://www.pinkbike.com/','',2,10,NULL,NULL,NULL,NULL,'src',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','a.f22','TITLE_CSS','','','.news-box h1','CONTENT_CSS','','','.news-ml','N','<style type=\"text/css\">︾<div style=\"line-height: 18px;\">','</style>︾</div>','#button-bar︾.news-d3︾.ppoll'),(59,1,75,1,1,'36kr资讯','2015-01-18 22:04:24','2015-01-18 22:06:20',0,0,0,0,1500,'UTF-8','http://www.36kr.com/category/cn-news\r\nhttp://www.36kr.com/category/breaking','',2,10,NULL,NULL,NULL,NULL,'src',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'NONE',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'yyyy-MM-dd HH:mm:ss','CSS','','.category-popular__list h1 a','TITLE_CSS','','','h1.single-post__title','CONTENT_CSS','','','section.article','N','<script︾<p>[<span>36氪','</script>︾]</p>','#BAIDU_DUP_wrapper_947522_0');

UNLOCK TABLES;

/*Table structure for table `jc_jautopost_files` */

DROP TABLE IF EXISTS `jc_jautopost_files`;

CREATE TABLE `jc_jautopost_files` (
  `jautopost_files_id` int(11) NOT NULL AUTO_INCREMENT,
  `md5_str` char(32) NOT NULL DEFAULT '',
  `path` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`jautopost_files_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `jc_jautopost_files` */

LOCK TABLES `jc_jautopost_files` WRITE;

UNLOCK TABLES;

/*Table structure for table `jc_jautopost_history` */

DROP TABLE IF EXISTS `jc_jautopost_history`;

CREATE TABLE `jc_jautopost_history` (
  `history_id` int(11) NOT NULL AUTO_INCREMENT,
  `channel_url` varchar(500) NOT NULL DEFAULT '' COMMENT '栏目地址',
  `content_url` varchar(500) NOT NULL DEFAULT '' COMMENT '内容地址',
  `title` varchar(500) DEFAULT NULL COMMENT '标题',
  `description` varchar(20) NOT NULL DEFAULT '' COMMENT '描述',
  `jautopost_id` int(11) DEFAULT NULL COMMENT '采集源',
  `content_id` int(11) DEFAULT NULL COMMENT '内容',
  PRIMARY KEY (`history_id`),
  KEY `fk_acquisition_history_acquisition` (`jautopost_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='采集历史记录表';

/*Data for the table `jc_jautopost_history` */

LOCK TABLES `jc_jautopost_history` WRITE;

UNLOCK TABLES;

/*Table structure for table `jc_jautopost_temp` */

DROP TABLE IF EXISTS `jc_jautopost_temp`;

CREATE TABLE `jc_jautopost_temp` (
  `temp_id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) DEFAULT NULL,
  `channel_url` varchar(500) NOT NULL DEFAULT '' COMMENT '栏目地址',
  `content_url` varchar(500) NOT NULL DEFAULT '' COMMENT '内容地址',
  `title` varchar(500) DEFAULT NULL COMMENT '标题',
  `finish_percent` int(3) NOT NULL DEFAULT '0' COMMENT '百分比',
  `description` varchar(20) NOT NULL DEFAULT '' COMMENT '描述',
  `seq` int(3) NOT NULL DEFAULT '0' COMMENT '顺序',
  PRIMARY KEY (`temp_id`),
  KEY `fk_jc_temp_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='采集进度临时表';

/*Data for the table `jc_jautopost_temp` */

LOCK TABLES `jc_jautopost_temp` WRITE;

UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
