package com.picpay.desafio.android.rule

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.desafio.android.rule.InstantCoroutineDispatcherRule
import org.junit.rules.RuleChain

val instantLiveDataAndCoroutineRules: RuleChain
    get() = RuleChain
        .outerRule(InstantCoroutineDispatcherRule())
        .around(InstantTaskExecutorRule())