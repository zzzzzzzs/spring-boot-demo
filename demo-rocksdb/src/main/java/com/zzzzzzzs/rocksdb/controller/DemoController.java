package com.zzzzzzzs.rocksdb.controller;

import com.zzzzzzzs.base.response.CommonResponse;
import com.zzzzzzzs.rocksdb.dto.RocksDBDTO;
import com.zzzzzzzs.rocksdb.utils.RocksDBUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.rocksdb.RocksDBException;

import java.util.*;

@SuppressWarnings("DuplicatedCode")
@Api(tags = "RocksDB")
@RestController
@RequestMapping(value = "/rocksdb")
public class DemoController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World";
    }

    @ApiOperation("列族，创建（如果不存在）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cfName", value = "列族", required = true, paramType = "query"),
    })
    @PostMapping("/cf")
    public CommonResponse<String> cfAdd(String cfName) throws RocksDBException {
        RocksDBUtil.cfAddIfNotExist(cfName);
        return CommonResponse.successResponse(cfName);
    }

    @ApiOperation("列族，删除（如果存在）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cfName", value = "列族", required = true, paramType = "query"),
    })
    @DeleteMapping("/cf")
    public CommonResponse<String> cfDelete(String cfName) throws RocksDBException {
        RocksDBUtil.cfDeleteIfExist(cfName);
        return CommonResponse.successResponse(cfName);
    }

    @ApiOperation("列族名（查询所有）")
    @GetMapping("/cf-all")
    public CommonResponse<Set<String>> cfAll() {
        Set<String> cfNames = RocksDBUtil.columnFamilyHandleMap.keySet();
        CommonResponse<Set<String>> response = CommonResponse.successResponse(cfNames);
        response.setTotal(cfNames.size());
        return response;
    }

    @ApiOperation("增")
    @PostMapping("/put")
    public CommonResponse<String> put(@RequestBody RocksDBDTO RocksDBDTO) throws RocksDBException {
        RocksDBUtil.put(RocksDBDTO.getCfName(), RocksDBDTO.getKey(), RocksDBDTO.getValue());
        return CommonResponse.successResponse("OK", 1);
    }

    @ApiOperation("增（批量）")
    @PostMapping("/batch-put")
    public CommonResponse<String> batchPut(@RequestBody List<RocksDBDTO> RocksDBDTOs) throws RocksDBException {
        Map<String, String> map = new HashMap<>();
        for (RocksDBDTO RocksDBDTO : RocksDBDTOs) {
            map.put(RocksDBDTO.getKey(), RocksDBDTO.getValue());
        }
        RocksDBUtil.batchPut(RocksDBDTOs.get(0).getCfName(), map);
        return CommonResponse.successResponse("OK", RocksDBDTOs.size());
    }

    @ApiOperation("删")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cfName", value = "列族", required = true, paramType = "query"),
            @ApiImplicitParam(name = "key", value = "键", required = true, paramType = "query"),
    })
    @DeleteMapping("/delete")
    public CommonResponse<RocksDBDTO> delete(String cfName, String key) throws RocksDBException {
        String value = RocksDBUtil.get(cfName, key);
        RocksDBUtil.delete(cfName, key);
        RocksDBDTO rocksDBDTO = RocksDBDTO.builder().cfName(cfName).key(key).value(value).build();
        return CommonResponse.successResponse(rocksDBDTO);
    }

    @ApiOperation("查")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cfName", value = "列族", required = true, paramType = "query"),
            @ApiImplicitParam(name = "key", value = "键", required = true, paramType = "query"),
    })
    @GetMapping("/get")
    public CommonResponse<RocksDBDTO> get(String cfName, String key) throws RocksDBException {
        String value = RocksDBUtil.get(cfName, key);
        RocksDBDTO rocksDBDTO = RocksDBDTO.builder().cfName(cfName).key(key).value(value).build();
        return CommonResponse.successResponse(rocksDBDTO);
    }

    @ApiOperation("查（多个键值对）")
    @PostMapping("/multiGetAsList")
    public CommonResponse<List<RocksDBDTO>> multiGetAsList(@RequestBody List<RocksDBDTO> RocksDBDTOs) throws RocksDBException {
        List<RocksDBDTO> list = new ArrayList<>();
        String cfName = RocksDBDTOs.get(0).getCfName();
        List<String> keys = new ArrayList<>(RocksDBDTOs.size());
        for (RocksDBDTO RocksDBDTO : RocksDBDTOs) {
            keys.add(RocksDBDTO.getKey());
        }
        Map<String, String> map = RocksDBUtil.multiGetAsMap(cfName, keys);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            RocksDBDTO rocksDBDTO = RocksDBDTO.builder().cfName(cfName).key(entry.getKey()).value(entry.getValue()).build();
            list.add(rocksDBDTO);
        }
        CommonResponse<List<RocksDBDTO>> response = CommonResponse.successResponse(list);
        response.setTotal(list.size());
        return response;
    }

    @ApiOperation("查所有键值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cfName", value = "列族", required = true, paramType = "query"),
    })
    @GetMapping("/get-all")
    public CommonResponse<List<RocksDBDTO>> getAll(String cfName) throws RocksDBException {
        List<RocksDBDTO> RocksDBDTOs = new ArrayList<>();
        Map<String, String> all = RocksDBUtil.getAll(cfName);
        for (Map.Entry<String, String> entry : all.entrySet()) {
            RocksDBDTO rocksDBDTO = RocksDBDTO.builder().cfName(cfName).key(entry.getKey()).value(entry.getValue()).build();
            RocksDBDTOs.add(rocksDBDTO);
        }
        CommonResponse<List<RocksDBDTO>> response = CommonResponse.successResponse(RocksDBDTOs);
        response.setTotal(RocksDBDTOs.size());
        return response;
    }

    @ApiOperation("分片查（键）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cfName", value = "列族", required = true, paramType = "query"),
    })
    @GetMapping("/get-keys")
    public CommonResponse<List<String>> getKeysFrom(String cfName) throws RocksDBException {
        List<String> data = new ArrayList<>();
        List<String> keys;
        String lastKey = null;
        while (true) {
            keys = RocksDBUtil.getKeysFrom(cfName, lastKey);
            if (keys.isEmpty()) {
                break;
            }
            lastKey = keys.get(keys.size() - 1);
            data.addAll(keys);
            keys.clear();
        }
        CommonResponse<List<String>> response = CommonResponse.successResponse(data);
        response.setTotal(data.size());
        return response;
    }

    @ApiOperation("查（所有键）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cfName", value = "列族", required = true, paramType = "query"),
    })
    @GetMapping("/get-all-key")
    public CommonResponse<List<String>> getAllKey(String cfName) throws RocksDBException {
        List<String> allKey = RocksDBUtil.getAllKey(cfName);
        CommonResponse<List<String>> response = CommonResponse.successResponse(allKey);
        response.setTotal(allKey.size());
        return response;
    }

    @ApiOperation("查总条数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cfName", value = "列族", required = true, paramType = "query"),
    })
    @GetMapping("/get-count")
    public CommonResponse<Integer> getCount(@RequestParam String cfName) throws RocksDBException {
        int count = RocksDBUtil.getCount(cfName);
        return CommonResponse.successResponse(count);
    }
}
