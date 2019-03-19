/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.restdocs.operation.preprocess;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.operation.OperationRequest;
import org.springframework.restdocs.operation.OperationRequestFactory;
import org.springframework.restdocs.operation.OperationResponse;
import org.springframework.restdocs.operation.OperationResponseFactory;

/**
 * An {@link OperationPreprocessor} that removes headers.
 *
 * @author Andy Wilkinson
 */
class HeaderRemovingOperationPreprocessor implements OperationPreprocessor {

	private final OperationRequestFactory requestFactory = new OperationRequestFactory();

	private final OperationResponseFactory responseFactory = new OperationResponseFactory();

	private final Set<String> headersToRemove;

	HeaderRemovingOperationPreprocessor(String... headersToRemove) {
		this.headersToRemove = new HashSet<>(Arrays.asList(headersToRemove));
	}

	@Override
	public OperationResponse preprocess(OperationResponse response) {
		return this.responseFactory.createFrom(response,
				removeHeaders(response.getHeaders()));
	}

	@Override
	public OperationRequest preprocess(OperationRequest request) {
		return this.requestFactory.createFrom(request,
				removeHeaders(request.getHeaders()));
	}

	private HttpHeaders removeHeaders(HttpHeaders originalHeaders) {
		HttpHeaders processedHeaders = new HttpHeaders();
		processedHeaders.putAll(originalHeaders);
		for (String headerToRemove : this.headersToRemove) {
			processedHeaders.remove(headerToRemove);
		}
		return processedHeaders;
	}
}
