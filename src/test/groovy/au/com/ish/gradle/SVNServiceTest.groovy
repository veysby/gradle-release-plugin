/*
 * Copyright 2012 ish group pty ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package au.com.ish.gradle

import org.junit.Test
import static org.junit.Assert.*

import org.gradle.testfixtures.ProjectBuilder

import au.com.ish.gradle.*

import groovy.mock.interceptor.*
import org.tmatesoft.svn.core.SVNException

class SvnServiceTest {    

    // tests parsing the url with 'tags'
    @Test
    public void testOnTag() {
        TestSvnService service = new TestSvnService()
        service.setSCMRemoteURL("http://svn.ish.com.au/ish/willow/tags/10.0/admin")
        assert service.onTag() == true
        assert service.getBranchName() == "10.0"

        service.setSCMRemoteURL("http://svn.ish.com.au/ish/angel/tags/10.0")
        assert service.onTag() == true
        assert service.getBranchName() == "10.0"
    }

    // tests parsing the url with 'branches'
    @Test
    public void testOnBranch() {
        TestSvnService service = new TestSvnService()
        service.setSCMRemoteURL("http://svn.ish.com.au/ish/willow/branches/stable/admin")
        assert service.onTag() == false
        assert service.getBranchName() == "stable"

        service.setSCMRemoteURL("http://svn.ish.com.au/ish/angel/branches/stable")
        assert service.onTag() == false
        assert service.getBranchName() == "stable"
    }

    // tests parsing the url with 'trunk'
    @Test
    public void testOnTrunk() {
        TestSvnService service = new TestSvnService()
        service.setSCMRemoteURL("http://svn.ish.com.au/ish/willow/trunk/admin")
        assert service.onTag() == false
        assert service.getBranchName() == "trunk"

        service.setSCMRemoteURL("http://svn.ish.com.au/ish/angel/trunk")
        assert service.onTag() == false
        assert service.getBranchName() == "trunk"
    }

    // tests parsing the root url with 'tags'
    @Test
    public void testRootURLonTags() {
        TestSvnService service = new TestSvnService()
        service.setSCMRemoteURL("http://svn.ish.com.au/ish/willow/tags/10.0/admin")
        assert service.getSvnRootURL() == "http://svn.ish.com.au/ish/willow"

        service.setSCMRemoteURL("http://svn.ish.com.au/ish/angel/tags/10.0")
        assert service.getSvnRootURL() == "http://svn.ish.com.au/ish/angel"
    }

    // tests parsing the root url with 'branches'
    @Test
    public void testRootURLonBranches() {
        TestSvnService service = new TestSvnService()
        service.setSCMRemoteURL("http://svn.ish.com.au/ish/willow/branches/stable/admin")
        assert service.getSvnRootURL() == "http://svn.ish.com.au/ish/willow"

        service.setSCMRemoteURL("http://svn.ish.com.au/ish/angel/branches/stable")
        assert service.getSvnRootURL() == "http://svn.ish.com.au/ish/angel"
    }

    // tests parsing the root url with 'trunk'
    @Test
    public void testRootURLonTrunk() {
        TestSvnService service = new TestSvnService()
        service.setSCMRemoteURL("http://svn.ish.com.au/ish/willow/trunk/admin")
        assert service.getSvnRootURL() == "http://svn.ish.com.au/ish/willow"

        service.setSCMRemoteURL("http://svn.ish.com.au/ish/angel/trunk")
        assert service.getSvnRootURL() == "http://svn.ish.com.au/ish/angel"
    }
}