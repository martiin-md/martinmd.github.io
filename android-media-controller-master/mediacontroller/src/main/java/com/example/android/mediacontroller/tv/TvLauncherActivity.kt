/*
 * Copyright 2018 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.mediacontroller.tv

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.android.mediacontroller.R
import com.example.android.mediacontroller.databinding.ActivityTvLaunchBinding

/**
 * Controls the Android TV UI by managing the appropriate Fragments.
 */
class TvLauncherActivity : FragmentActivity() {
    private lateinit var binding: ActivityTvLaunchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}