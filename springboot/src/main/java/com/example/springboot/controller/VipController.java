package com.example.springboot.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.User;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Result;



import com.example.springboot.service.IVipService;
import com.example.springboot.entity.Vip;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ada
 * @since 2023-06-29
 */
@RestController
@RequestMapping("/vip")
        public class VipController {
    
@Resource
    private IVipService vipService;

//Nuevas incorporaciones y modificaciones.
@PostMapping
    public Result save(@RequestBody Vip vip){
       vipService.saveOrUpdate(vip);
          return Result.success();
        }

@DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
       vipService.removeById(id);
          return Result.success();
        }

@PostMapping("/del/batch")
   public Result deleteBatch(@RequestBody List<Integer> ids) {
       vipService.removeByIds(ids);
          return Result.success();
        }

//buscar todos
@GetMapping
   public Result findALL(){
          return Result.success(vipService.list());

        }

@GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(vipService.getById(id));

        }

@GetMapping("/page")
    public Result findPage(@RequestParam String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Vip> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        queryWrapper.orderByDesc("id");
        return Result.success(vipService.page(new Page<>(pageNum, pageSize), queryWrapper));
        }

    /**
     * export interface
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Vip> list = vipService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);


        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("VipInformation", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<Vip> list = reader.readAll(Vip.class);
//        System.out.println(list);
        vipService.saveBatch(list);
        return Result.success(true);
    }
}

