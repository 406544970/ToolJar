package lh.units;


import lh.model.ResultVO;
import lh.model.ResultVOPage;
import lh.model.ResultVOPageTotal;
import lh.model.ResultVOTotal;
import lh.myenum.ResultCode;

import java.util.Date;
import java.util.List;

/**
 * @author 梁昊
 * @date 2019/8/25
 * @function
 * @editLog
 */
public class ResultStruct {
    private static <T, S, V> S successPrivate(T data, Class<S> sClass
            , int page, int limit, long totalCount
            , List<V> totalList) {
        String name = sClass.getName();
        S resultStruct;
        switch (name) {
            case "lh.model.ResultVO":
                resultStruct = (S) new ResultVO<T>();
                ((ResultVO) resultStruct).setCode(ResultCode.SUCCESS.getCode());
                ((ResultVO) resultStruct).setMsg(ResultCode.SUCCESS.getMessage());
                ((ResultVO) resultStruct).setData(data);
                break;
            case "lh.model.ResultVOPage":
                resultStruct = (S) new ResultVOPage<T>();
                ((ResultVOPage) resultStruct).setCode(ResultCode.SUCCESS.getCode());
                ((ResultVOPage) resultStruct).setMsg(ResultCode.SUCCESS.getMessage());
                ((ResultVOPage) resultStruct).setData(data);
                ((ResultVOPage) resultStruct).setPage(page);
                ((ResultVOPage) resultStruct).setLimit(limit);
                ((ResultVOPage) resultStruct).setCount(totalCount);
                break;
            case "lh.model.ResultVOPageTotal":
                resultStruct = (S) new ResultVOPageTotal<T, V>();
                ((ResultVOPageTotal) resultStruct).setCode(ResultCode.SUCCESS.getCode());
                ((ResultVOPageTotal) resultStruct).setMsg(ResultCode.SUCCESS.getMessage());
                ((ResultVOPageTotal) resultStruct).setData(data);
                ((ResultVOPageTotal) resultStruct).setPage(page);
                ((ResultVOPageTotal) resultStruct).setLimit(limit);
                ((ResultVOPageTotal) resultStruct).setCount(totalCount);
                ((ResultVOPageTotal) resultStruct).setTotalData(totalList);

                break;
            case "lh.model.ResultVOTotal":
                resultStruct = (S) new ResultVOTotal<T, V>();
                ((ResultVOTotal) resultStruct).setCode(ResultCode.SUCCESS.getCode());
                ((ResultVOTotal) resultStruct).setMsg(ResultCode.SUCCESS.getMessage());
                ((ResultVOTotal) resultStruct).setData(data);
                ((ResultVOTotal) resultStruct).setTotalData(totalList);
                break;
            default:
                resultStruct = (S) new ResultVO<T>();
                ((ResultVO) resultStruct).setCode(ResultCode.SUCCESS.getCode());
                ((ResultVO) resultStruct).setMsg(ResultCode.SUCCESS.getMessage());
                ((ResultVO) resultStruct).setData(data);
                break;
        }
        return resultStruct;
    }

    /**
     * 成功
     *
     * @param data 数据
     * @param <T>  泛型
     * @return
     */
    public static <T> ResultVO success(T data) {
        return successPrivate(data, ResultVO.class, 1, 10, 0, null);
    }

    /**
     * 成功，带分页功能
     *
     * @param data       数据
     * @param page       当前页面数
     * @param limit      每页条数
     * @param totalCount 数据条数
     * @param <T>        泛型
     * @return
     */
    public static <T> ResultVOPage successPage(T data
            , int page, int limit, long totalCount) {
        return successPrivate(data, ResultVOPage.class, page, limit, totalCount, null);
    }

    /**
     * 成功，带汇总功能
     *
     * @param data      数据
     * @param totalList 汇总数据totalList
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V> ResultVOTotal successTotal(T data
            , List<V> totalList) {
        return successPrivate(data, ResultVOTotal.class, 1, 10, 0, totalList);
    }

    /**
     * 成功，带汇总功能，带分页功能
     *
     * @param data       数据
     * @param page       当前页面数
     * @param limit      每页条数
     * @param totalCount 数据条数
     * @param totalList  汇总数据totalList
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V> ResultVOPageTotal successPageTotal(T data
            , int page, int limit, long totalCount
            , List<V> totalList) {
        return successPrivate(data, ResultVOPageTotal.class, page, limit, totalCount, totalList);
    }

    /**
     * 失败
     *
     * @param sClass 返回类
     * @param <S>    泛型
     * @return
     */
    public static <S> S error(Class<S> sClass) {
        return error(ResultCode.ERROR.getMessage(), sClass, null);
    }

    private static <T> Object getClassDefault(Class<T> tClass){
        Object object = null;
        if (tClass != null) {
            String tName = tClass.getName();
            switch (tName) {
                case "java.util.Date":
                    object = new Date();
                    break;
                case "java.lang.Boolean":
                case "boolean":
                    object = false;
                    break;
                case "int":
                case "float":
                case "byte":
                case "short":
                case "double":
                case "java.lang.Integer":
                case "java.lang.Float":
                case "java.lang.Byte":
                case "java.lang.Short":
                case "java.lang.Double":
                    object = 0;
                    break;
                default:
                    break;
            }
        }
        return object;
    }
    /**
     * 失败
     *
     * @param message 失败消息
     * @param sClass  返回类
     * @param <S>     泛型
     * @return
     */
    public static <S, T> S error(String message, Class<S> sClass, Class<T> tClass) {
        String name = sClass.getClass().getName();
        S resultStruct;
        switch (name) {
            case "ResultVO":
                resultStruct = (S) new ResultVO();
                ((ResultVO) resultStruct).setCode(ResultCode.ERROR.getCode());
                if (message != null) {
                    ((ResultVO) resultStruct).setMsg(message);
                }
                ((ResultVO) resultStruct).setData(getClassDefault(tClass));
                break;
            case "ResultVOPage":
                resultStruct = (S) new ResultVOPage();
                ((ResultVOPage) resultStruct).setCode(ResultCode.ERROR.getCode());
                if (message != null) {
                    ((ResultVOPage) resultStruct).setMsg(message);
                }
                ((ResultVOPage) resultStruct).setData(getClassDefault(tClass));
                break;
            case "ResultVOPageTotal":
                resultStruct = (S) new ResultVOPageTotal();
                ((ResultVOPageTotal) resultStruct).setCode(ResultCode.ERROR.getCode());
                if (message != null) {
                    ((ResultVOPageTotal) resultStruct).setMsg(message);
                }
                ((ResultVOPageTotal) resultStruct).setData(getClassDefault(tClass));
                break;
            case "ResultVOTotal":
                resultStruct = (S) new ResultVOTotal();
                ((ResultVOTotal) resultStruct).setCode(ResultCode.ERROR.getCode());
                if (message != null) {
                    ((ResultVOTotal) resultStruct).setMsg(message);
                }
                ((ResultVOTotal) resultStruct).setData(getClassDefault(tClass));
                break;
            default:
                resultStruct = (S) new ResultVO();
                ((ResultVO) resultStruct).setCode(ResultCode.ERROR.getCode());
                if (message != null) {
                    ((ResultVO) resultStruct).setMsg(message);
                }
                ((ResultVO) resultStruct).setData(getClassDefault(tClass));
                break;
        }
        return resultStruct;
    }
}
