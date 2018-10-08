package ca.eloas.presentations.streams;

import org.raml.api.RamlMediaType;
import org.raml.api.RamlResourceMethod;
import org.raml.builder.BodyBuilder;
import org.raml.builder.MethodBuilder;
import org.raml.builder.ResponseBuilder;
import org.raml.builder.TypeBuilder;
import org.raml.jaxrs.plugins.TypeHandler;
import org.raml.jaxrs.plugins.TypeSelector;
import org.raml.jaxrs.types.TypeRegistry;

import java.io.IOException;
import java.util.Collection;

/**
 * Created. There, you have it.
 */
public class ComplexLoop {
    public void complicated(TypeRegistry typeRegistry, Collection<RamlResourceMethod> methods, TypeSelector selector,
                            MethodBuilder methodBuilder) throws IOException {

        ResponseBuilder responseBuilder = null;
        for (RamlResourceMethod method : methods) {

            if (!method.getProducedType().isPresent()) {
                continue;
            }

            for (RamlMediaType producedMediaType : method.getProducedMediaTypes()) {

                if (responseBuilder == null) {

                    responseBuilder = ResponseBuilder.response(200);
                }

                BodyBuilder body = BodyBuilder.body(producedMediaType.toStringRepresentation());
                responseBuilder.withBodies(body);

                TypeHandler typeHandler = selector.pickTypeWriter(method, producedMediaType);
                TypeBuilder type = typeHandler.writeType(typeRegistry, method.getProducedType().get());
                body.ofType(type);
            }
        }

        if (responseBuilder != null) {
            methodBuilder.withResponses(responseBuilder);
        }
    }
}
