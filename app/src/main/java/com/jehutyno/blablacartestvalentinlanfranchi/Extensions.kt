package com.jehutyno.blablacartestvalentinlanfranchi

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

inline val AppCompatActivity.app get() = application as AndroidApp
inline val Fragment.app get() = appCompatActivity.application as AndroidApp
inline val Fragment.appCompatActivity get() = requireActivity() as AppCompatActivity
inline val Context.app get() = applicationContext as AndroidApp