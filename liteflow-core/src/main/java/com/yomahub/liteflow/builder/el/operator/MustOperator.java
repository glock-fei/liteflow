package com.yomahub.liteflow.builder.el.operator;

import cn.hutool.core.util.StrUtil;
import com.yomahub.liteflow.builder.el.operator.base.BaseOperator;
import com.yomahub.liteflow.builder.el.operator.base.OperatorHelper;
import com.yomahub.liteflow.enums.ParallelStrategyEnum;
import com.yomahub.liteflow.flow.element.condition.WhenCondition;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * EL 规则中的 must 的操作符
 *
 * @author luo yi
 * @since 2.11.0
 */
public class MustOperator extends BaseOperator<WhenCondition> {

	@Override
	public WhenCondition build(Object[] objects) throws Exception {
		OperatorHelper.checkObjectSizeEqTwo(objects);

		WhenCondition whenCondition = OperatorHelper.convert(objects[0], WhenCondition.class);

		String specifyIds = OperatorHelper.convert(objects[1], String.class);

		// 解析指定完成的任务 ID 集合
		Set<String> specifyIdSet = Arrays.stream(specifyIds.replace(StrUtil.SPACE, StrUtil.EMPTY).split(",")).collect(Collectors.toSet());

		whenCondition.setSpecifyIdSet(specifyIdSet);
		whenCondition.setParallelStrategy(ParallelStrategyEnum.SPECIFY);
		return whenCondition;
	}

}