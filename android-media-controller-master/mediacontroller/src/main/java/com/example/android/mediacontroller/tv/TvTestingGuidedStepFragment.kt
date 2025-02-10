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
import androidx.leanback.app.GuidedStepSupportFragment
import androidx.leanback.widget.GuidanceStylist
import androidx.leanback.widget.GuidedAction
import androidx.core.content.ContextCompat
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.android.mediacontroller.MediaAppDetails
import com.example.android.mediacontroller.R
import com.example.android.mediacontroller.Test
import com.example.android.mediacontroller.formatTvDetailsString
import com.example.android.mediacontroller.runPauseTest
import com.example.android.mediacontroller.runPlayFromMediaIdTest
import com.example.android.mediacontroller.runPlayFromSearchTest
import com.example.android.mediacontroller.runPlayFromUriTest
import com.example.android.mediacontroller.runPlayTest
import com.example.android.mediacontroller.runSeekToTest
import com.example.android.mediacontroller.runSkipToItemTest
import com.example.android.mediacontroller.runSkipToNextTest
import com.example.android.mediacontroller.runSkipToPrevTest
import com.example.android.mediacontroller.runStopTest
import java.text.DateFormat
import java.util.Date

/**
 * Lists test options and displays selected MediaController details
 */
class TvTestingGuidedStepFragment : GuidedStepSupportFragment() {
    private var mediaAppDetails: MediaAppDetails? = null
    private var mediaController: MediaControllerCompat? = null
    private var mediaBrowser: MediaBrowserCompat? = null

    private var query: String = ""

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mediaAppDetails = arguments?.getParcelable("App Details")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Test.androidResources = resources

        activity?.let {
            if (it is TvTestingActivity) {
                mediaBrowser = it.getBrowser()
                mediaController = it.getController()
            }
        }
    }

    override fun onCreateGuidance(savedInstanceState: Bundle?): GuidanceStylist.Guidance {
        return GuidanceStylist.Guidance(
                mediaAppDetails?.appName,
                getString(R.string.tv_intro_description),
                getString(R.string.tv_intro_breadcrumb),
                null
        )
    }

    private fun buildAction(
            id: Long,
            title: String,
            desc: String,
            usesQuery: Boolean = false
    ): GuidedAction {
        val action = GuidedAction.Builder(context)
                .id(id)
                .title(title)
                .description(desc)
                .multilineDescription(true)
                .editTitle("")
                .editable(usesQuery)
                .hasNext(usesQuery)
                .build()
        return action
    }

    override fun onCreateActions(actions: MutableList<GuidedAction>, savedInstanceState: Bundle?) {
        actions.add(buildAction(
                PLAY_TEST,
                getString(R.string.play_test_title),
                getString(R.string.play_test_desc)
        ))
        actions.add(buildAction(
                PLAY_FROM_SEARCH_TEST,
                getString(R.string.play_search_test_title),
                getQueryTestDesc(R.string.play_search_test_desc),
                usesQuery = true
        ))
        actions.add(buildAction(
                PLAY_FROM_MEDIA_ID_TEST,
                getString(R.string.play_media_id_test_title),
                getQueryTestDesc(R.string.play_media_id_test_desc),
                usesQuery = true
        ))
        actions.add(buildAction(
                PLAY_FROM_URI_TEST,
                getString(R.string.play_uri_test_title),
                getQueryTestDesc(R.string.play_uri_test_desc),
                usesQuery = true
        ))
        actions.add(buildAction(
                PAUSE_TEST,
                getString(R.string.pause_test_title),
                getString(R.string.pause_test_desc)
        ))
        actions.add(buildAction(
                STOP_TEST,
                getString(R.string.stop_test_title),
                getString(R.string.stop_test_desc)
        ))
        actions.add(buildAction(
                SKIP_TO_NEXT_TEST,
                getString(R.string.skip_next_test_title),
                getString(R.string.skip_next_test_desc)
        ))
        actions.add(buildAction(
                SKIP_TO_PREV_TEST,
                getString(R.string.skip_prev_test_title),
                getString(R.string.skip_prev_test_desc)
        ))
        actions.add(buildAction(
                SKIP_TO_ITEM_TEST,
                getString(R.string.skip_item_test_title),
                getQueryTestDesc(R.string.skip_item_test_desc),
                usesQuery = true
        ))
        actions.add(buildAction(
                SEEK_TO_TEST,
                getString(R.string.seek_test_title),
                getQueryTestDesc(R.string.seek_test_desc),
                usesQuery = true
        ))
        actions.add(actions.size, GuidedAction.Builder(context)
                .id(REFRESH_INFO)
                .title(getString(R.string.logs_trigger_title))
                .build()
        )
    }

    // For tests that use the query field
    override fun onGuidedActionEditedAndProceed(action: GuidedAction?): Long {
        mediaController?.let {
            query = action?.editTitle.toString()
            when (action?.id) {
                PLAY_FROM_SEARCH_TEST -> {
                    action.description = getQueryTestDesc(R.string.play_search_test_desc, query)
                    runPlayFromSearchTest(1, query, it, { foo, bar, _ -> })
                }
                PLAY_FROM_MEDIA_ID_TEST -> {
                    action.description = getQueryTestDesc(R.string.play_media_id_test_desc, query)
                    runPlayFromMediaIdTest(2, query, it, { foo, bar, _ -> })
                }
                PLAY_FROM_URI_TEST -> {
                    action.description = getQueryTestDesc(R.string.play_uri_test_desc, query)
                    runPlayFromUriTest(3, query, it, { foo, bar, _ -> })
                }
                SKIP_TO_ITEM_TEST -> {
                    action.description = getQueryTestDesc(R.string.skip_item_test_desc, query)
                    runSkipToItemTest(8, query, it, { foo, bar, _ -> })
                }
                SEEK_TO_TEST -> {
                    action.description = getQueryTestDesc(R.string.seek_test_desc, query)
                    runSeekToTest(9, query, it, { foo, bar, _ -> })
                }
                else -> {
                    query = ""
                    Toast.makeText(
                            activity,
                            getString(R.string.tv_invalid_test),
                            Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        return GuidedAction.ACTION_ID_CURRENT
    }

    override fun onGuidedActionEditCanceled(action: GuidedAction?) {
        query = ""
        when (action?.id) {
            PLAY_FROM_SEARCH_TEST -> {
                action.description = getQueryTestDesc(R.string.play_search_test_desc)
            }
            PLAY_FROM_MEDIA_ID_TEST -> {
                action.description = getQueryTestDesc(R.string.play_media_id_test_desc)
            }
            PLAY_FROM_URI_TEST -> {
                action.description = getQueryTestDesc(R.string.play_uri_test_desc)
            }
            SKIP_TO_ITEM_TEST -> {
                action.description = getQueryTestDesc(R.string.skip_item_test_desc)
            }
            SEEK_TO_TEST -> {
                action.description = getQueryTestDesc(R.string.seek_test_desc)
            }
            else -> Toast.makeText(
                    activity,
                    getString(R.string.tv_invalid_test),
                    Toast.LENGTH_LONG
            ).show()
        }
    }

    // For tests that do not use the query field
    override fun onGuidedActionClicked(action: GuidedAction?) {
        val queryTests = longArrayOf(
                PLAY_FROM_SEARCH_TEST,
                PLAY_FROM_MEDIA_ID_TEST,
                PLAY_FROM_URI_TEST,
                SKIP_TO_ITEM_TEST,
                SEEK_TO_TEST
        )
        mediaController?.let {
            when (action?.id) {
                PLAY_TEST -> runPlayTest(0, it, { foo, bar, _ -> })
                PAUSE_TEST -> runPauseTest(4, it, { foo, bar, _ -> })
                STOP_TEST -> runStopTest(5, it, { foo, bar, _ -> })
                SKIP_TO_NEXT_TEST -> runSkipToNextTest(6, it, { foo, bar, _ -> })
                SKIP_TO_PREV_TEST -> runSkipToPrevTest(7, it, { foo, bar, _ -> })
                REFRESH_INFO -> guidanceStylist.descriptionView.text = it.formatTvDetailsString()
                else -> {
                    if (action?.id == null || !queryTests.contains(action.id)) {
                        Toast.makeText(
                                activity,
                                getString(R.string.tv_invalid_test),
                                Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun logTestUpdate(logTag: String, message: String) {
        val date = DateFormat
                .getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG)
                .format(Date())
        Log.i("TvTestingFragment", "[$date] <$logTag>:\n$message")
        activity?.runOnUiThread {
            guidanceStylist.descriptionView.text = mediaController?.formatTvDetailsString()
            guidanceStylist.breadcrumbView.text = message
        }
    }

    private fun getQueryTestDesc(id: Int, query: String? = null): String {
        return if (query == null) {
            getString(R.string.tv_text_input, getString(id))
        } else {
            getString(R.string.tv_text_input_with_query, getString(id), query)
        }
    }

    companion object {
        const val REFRESH_INFO = -1L
        const val PLAY_TEST = 0L
        const val PLAY_FROM_SEARCH_TEST = 1L
        const val PLAY_FROM_MEDIA_ID_TEST = 2L
        const val PLAY_FROM_URI_TEST = 3L
        const val PAUSE_TEST = 4L
        const val STOP_TEST = 5L
        const val SKIP_TO_NEXT_TEST = 6L
        const val SKIP_TO_PREV_TEST = 7L
        const val SKIP_TO_ITEM_TEST = 8L
        const val SEEK_TO_TEST = 9L
    }
}