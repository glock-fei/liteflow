package com.yomahub.liteflow.builder.el;

import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * ELWrapper是所有组件的抽象父类
 * 定义所有EL表达式的公有变量 tag、id、data、maxWaitSeconds 以及 子表达式列表 ELWrapperList
 *
 * @author gezuao
 * @since 2.11.1
 */
public abstract class ELWrapper {

    private final List<ELWrapper> elWrapperList = new ArrayList<>();

    private String tag;
    private String id;
    private String dataName;
    private String data;
    private Integer maxWaitSeconds;

    protected void addWrapper(ELWrapper wrapper){
        this.elWrapperList.add(wrapper);
    }

    protected void addWrapper(ELWrapper... wrappers){
        this.elWrapperList.addAll(Arrays.asList(wrappers));
    }

    protected void addWrapper(ELWrapper wrapper, int index){
        this.elWrapperList.add(index, wrapper);
    }

    protected void setWrapper(ELWrapper wrapper, int index){
        this.elWrapperList.set(index, wrapper);
    }

    protected ELWrapper getFirstWrapper(){
        return this.elWrapperList.get(0);
    }

    protected List<ELWrapper> getElWrapperList(){
        return this.elWrapperList;
    }

    protected void setTag(String tag){
        this.tag = tag;
    }

    protected String getTag(){
        return this.tag;
    }

    protected void setId(String id){
        this.id = id;
    }

    protected String getId(){
        return this.id;
    }

    protected void setData(String data){
        this.data = data;
    }

    protected String getData(){
        return this.data;
    }

    protected void setDataName(String dataName){
        this.dataName = dataName;
    }

    protected String getDataName(){
        return this.dataName;
    }

    protected void setMaxWaitSeconds(Integer maxWaitSeconds){
        this.maxWaitSeconds = maxWaitSeconds;
    }

    protected Integer getMaxWaitSeconds(){
        return this.maxWaitSeconds;
    }

    /**
     * 设置组件标记内容
     *
     * @param tag 标记内容
     * @return {@link ELWrapper}
     */
    protected abstract ELWrapper tag(String tag);

    /**
     * 设置组件的id
     *
     * @param id 编号
     * @return {@link ELWrapper}
     */
    protected abstract ELWrapper id(String id);

    /**
     * 设置表达式data属性
     *
     * @param dataName 数据名称
     * @param object   JavaBean
     * @return {@link ELWrapper}
     */
    protected abstract ELWrapper data(String dataName, Object object);

    /**
     * 设置表达式data属性
     *
     * @param dataName   数据名称
     * @param jsonString JSON格式字符串
     * @return {@link ELWrapper}
     */
    protected abstract ELWrapper data(String dataName, String jsonString);

    /**
     * 设置data属性
     *
     * @param dataName 数据名称
     * @param jsonMap  键值映射
     * @return {@link ELWrapper}
     */
    protected abstract ELWrapper data(String dataName, Map<String, Object> jsonMap);

    protected ELWrapper maxWaitSeconds(Integer maxWaitSeconds){
        setMaxWaitSeconds(maxWaitSeconds);
        return this;
    }

    /**
     * 非格式化输出EL表达式
     *
     * @return {@link String}
     */
    public String toEL(){
        StringBuilder paramContext = new StringBuilder();
        String elContext = toEL(null, paramContext);
        return paramContext.append(elContext).toString();
    }

    /**
     * 是否格式化输出树形结构的表达式
     *
     * @param format 格式
     * @return {@link String}
     */
    public String toEL(boolean format){
        StringBuilder paramContext = new StringBuilder();
        String elContext;
        if(!format){
            elContext = toEL(null, paramContext);
        } else {
            elContext = toEL(0, paramContext);
        }
        return paramContext.append(elContext).toString();
    }

    /**
     * 格式化输出EL表达式
     *
     * @param depth        深度
     * @param paramContext 参数上下文，用于输出data参数内容
     * @return {@link String}
     */
    protected abstract String toEL(Integer depth, StringBuilder paramContext);

    /**
     * 处理EL表达式的共有属性
     *
     * @param elContext    EL表达式上下文
     * @param paramContext 参数上下文
     */
    protected void processWrapperProperty(StringBuilder elContext, StringBuilder paramContext){
        if(this.getId() != null){
            elContext.append(StrUtil.format(".id(\"{}\")", this.getId()));
        }
        if(this.getTag() != null){
            elContext.append(StrUtil.format(".tag(\"{}\")", this.getTag()));
        }
        if(this.getData() != null){
            elContext.append(StrUtil.format(".data({})", this.getDataName()));
            paramContext.append(StrUtil.format("{} = '{}';\n", this.getDataName(), this.getData()));
        }
        if(this.getMaxWaitSeconds() != null){
            elContext.append(StrUtil.format(".maxWaitSeconds({})", String.valueOf(this.getMaxWaitSeconds())));
        }
    }

    /**
     * 处理格式化输出 EL表达式换行符
     *
     * @param elContext EL 上下文
     * @param depth     深度
     */
    protected void processWrapperNewLine(StringBuilder elContext, Integer depth){
        if(depth != null){
            elContext.append("\n");
        }
    }

    /**
     * 处理格式化输出 EL表达式制表符
     *
     * @param elContext EL 上下文
     * @param depth     深度
     */
    protected void processWrapperTabs(StringBuilder elContext, Integer depth){
        if(depth != null) {
            elContext.append(StrUtil.repeat(ELBus.TAB, depth));
        }
    }
}