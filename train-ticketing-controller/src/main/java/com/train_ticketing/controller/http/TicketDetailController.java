package com.train_ticketing.controller.http;

import com.train_ticketing.application.model.TicketDetailDTO;
import com.train_ticketing.application.service.ticket.TicketDetailAppService;
import com.train_ticketing.controller.model.enums.ResultUtil;
import com.train_ticketing.controller.model.vo.ResultMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket")
@Slf4j
@RequiredArgsConstructor
public class TicketDetailController {
    private final TicketDetailAppService ticketDetailAppService;

    @GetMapping("/ping/java")
    public ResponseEntity<Object> ping() throws InterruptedException {
        // Giả lập tác vụ mất thời gian
        Thread.sleep(1000);

        // Trả về response với status OK
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response("OK"));
    }

    /**
     * Get ticket detail
     *
     * @param ticketId
     * @param detailId
     * @return ResultUtil
     */
    @GetMapping("/{ticketId}/detail/{detailId}")
    public ResultMessage<TicketDetailDTO> getTicketDetail(
            @PathVariable("ticketId") Long ticketId,
            @PathVariable("detailId") Long detailId,
            @RequestParam(name = "version", required = false) Long version
    ) {
        return ResultUtil.data(ticketDetailAppService.getTicketDetailById(detailId, version));
    }

    /**
     * order by User
     *
     * @param ticketId
     * @param detailId
     * @return ResultUtil
     */
    @GetMapping("/{ticketId}/detail/{detailId}/order")
    public boolean orderTicketByUser(
            @PathVariable("ticketId") Long ticketId,
            @PathVariable("detailId") Long detailId
    ) {
        return ticketDetailAppService.orderTicketByUser(detailId);
    }

    // Lớp Response để trả về JSON response
    public static class Response {
        private String status;

        public Response(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
